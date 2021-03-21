package com.latest;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

import java.util.ArrayList;
import java.util.HashMap;

import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Process extends UntypedAbstractActor {
	
	
	private class State {
		  private final int est;
		  private final int ballot;

		  public State(int e, int b) {
		    est = e;
		    ballot = b;
		  }
	}
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> members;
	private int id; // process id
	private int N; // total number of processes
	private int ballot; // ballot number
	private int proposal; // proposal value
	private int readBallot; // read ballot number
	private int imposeBallot; // impose ballot number
	private int estimate;  // estimate proposed value
	private int nbAbortRead;
	private int nbAbortImpose;
	private HashMap<Integer, State> states;
	private int nAck;
	private int initialProposal; 
	private boolean decided; 
	private boolean isFaulty;
	private boolean isSilent;
	private boolean hold;
	private double crashProba;
	private long startTime;

	public Process(int i, int n) {
		this.N = n; id = i;
		this.decided = false;
		this.ballot = id - N; readBallot = 0; imposeBallot = id - N;
		this.estimate = -1; proposal = -1; nbAbortRead = 0; nbAbortImpose = 0;
		this.isFaulty = false;
		this.isSilent = false;
		this.hold = false;
		this.crashProba = 0;
		this.startTime = 0;
		this.members = new ArrayList<ActorRef>();
		this.states = new HashMap<Integer, State>();
		this.nAck = 0;
	}
	
	public static Props createProcess(int i, int n) {
		return Props.create(Process.class, () -> {
			return new Process(i, n);
		});
	}
	
	public void receiveRead(ReadMsg msg) {
		int ballotNumber = msg.getBallot();
		if (this.readBallot > ballotNumber || this.imposeBallot > ballotNumber) {
			AbortMsg abortMessage = new AbortMsg(ballotNumber, true);
			getSender().tell(abortMessage, getSelf());
		}
		
		else {
			this.readBallot = ballotNumber;
			getSender().tell(new GatherMsg(id, ballotNumber, this.imposeBallot, this.estimate), getSelf());
		}
	}
	
	public void receiveAbort(AbortMsg msg) {
		// on distingue les différents abort et si il y a une majorité on repropose
		boolean isMajorityReached = false;
		if(msg.getIsReadAbortd()) {
			this.nbAbortRead++;
			isMajorityReached = this.nbAbortRead++ > this.N/2; 
		}
		if(msg.getIsReadAbortd()) {
			this.nbAbortImpose++;
			isMajorityReached = this.nbAbortImpose++ > this.N/2; 
		}
		if(!this.hold && !this.decided && isMajorityReached) {
			this.nbAbortRead = 0;
			this.nbAbortImpose = 0;
			getSelf().tell(new LaunchMsg(this.initialProposal), getSelf()); 
		}
		// remise des compteurs à 0 quand on a atteint une majorité
		return;
	}
	
	public void receiveGather(GatherMsg msg) {
		this.states.put(msg.getId(), new State(msg.getEstimate(), msg.getBallot()));
		this.nbAbortRead++;
		if(this.states.size() > this.N/2) {
			int maxEstBallot = 0;
			int estimate = 0;
			for(int i : this.states.keySet()) {
				int ballot = this.states.get(i).ballot;
				if(ballot > maxEstBallot) {
					maxEstBallot = ballot;
					estimate = this.states.get(i).est;
				}
			}
			
			if(maxEstBallot > 0 && estimate > -1) {
				this.proposal = estimate;
			}
			
			this.states = new HashMap<Integer, State>();
			
			ImposeMsg imp = new ImposeMsg(this.ballot, this.proposal);
			for(ActorRef a : this.members) {
				a.tell(imp, getSelf());
			}
		}
	}
	
	public void receiveImpose(ImposeMsg msg) {
		int ballot = msg.getBallot();
		if(this.readBallot > ballot || this.imposeBallot > ballot) {
			getSender().tell(new AbortMsg(ballot, false), getSelf());
		}
		
		else {
			this.estimate = msg.getProposal();
			this.imposeBallot = ballot;
			getSender().tell(new AckMsg(ballot), getSelf());
		}
	}
	
	public void receiveAck(AckMsg msg) {
		this.nbAbortImpose++;
		this.nAck += 1;
		if (this.nAck > N/2) {
			DecideMsg dec = new DecideMsg(proposal);
			for(ActorRef a : members) {
				a.tell(dec, getSelf());
			}
			this.nAck = 0;
		}
	}
	
	public void receiveDecide(DecideMsg msg) {
		if(!decided) {
			long endTime = System.currentTimeMillis();
			log.info("p" + Integer.toString(id) + " decides " + Integer.toString(msg.getProposal()) + " in " + Long.toString(endTime - startTime) + "ms");
			for(ActorRef a : members) {
				a.tell(msg, getSelf());
			}
		}
		decided = true;
	}
	
	
	@Override
	public void onReceive(Object message) throws Throwable {
		if(!isSilent && isFaulty) {
			if (Math.random() < crashProba) {
				isSilent = true;
			}
		}
		else if(message instanceof Members) {
			Members m = (Members) message;
			receiveMembers(m);
		}
		else if(message instanceof CrashMsg) {
			CrashMsg m = (CrashMsg) message;
			crash(m);
		}		
		else if(message instanceof HoldMsg) {
//		    log.info("HOLD Message received by p" + self().path().name() );
			hold = true;
		}		
		else if(message instanceof LaunchMsg) {
			LaunchMsg m = (LaunchMsg) message;
			receiveLaunch(m);
		}		
		else if(message instanceof Timer) {
			Timer m = (Timer) message;
			startTime = m.getStart();
		}		
		else if(message instanceof ReadMsg) {
//			log.info("p" + self().path().name() + " received READ from p" + getSender().path().name());
			ReadMsg m = (ReadMsg) message;
			receiveRead(m);
		}		
		else if(message instanceof AbortMsg) {
//			log.info("p" + self().path().name() + " received ABORT from p" + getSender().path().name());
			AbortMsg m = (AbortMsg) message;
			receiveAbort(m);
		}
		
		else if(message instanceof GatherMsg) {
//			log.info("p" + self().path().name() + " received GATHER from p" + getSender().path().name());
			GatherMsg m = (GatherMsg) message;
			receiveGather(m);
		}
		
		else if(message instanceof ImposeMsg) {
//			log.info("p" + self().path().name() + " received IMPOSE from p" + getSender().path().name());
			ImposeMsg m = (ImposeMsg) message;
			receiveImpose(m);
		}
		
		else if(message instanceof AckMsg) {
//			  log.info("p" + self().path().name() + " received ACK from p" + getSender().path().name());

			AckMsg m = (AckMsg) message;
			receiveAck(m);
		}
		
		else if(message instanceof DecideMsg) {
//			log.info("p" + self().path().name() + " received DECIDE from p" + getSender().path().name());
			DecideMsg m = (DecideMsg) message;
			receiveDecide(m);
		}	
	}
	
	public void receiveMembers(Members m) {
		this.members = m.members;
	}
	
	public void crash(CrashMsg msg) {	
		this.isFaulty = true;
		this.crashProba = msg.getCrashProba();
	}
	
	public void receiveLaunch(LaunchMsg s) {		
		this.initialProposal = s.getProposal();
		propose(s.getProposal());
	}
	
	public void propose(int p) {		
		this.ballot += this.N;
		this.proposal = p;
		this.nAck = 0;
		this.nbAbortRead = 0;
		this.nbAbortImpose = 0; 
		this.states = new HashMap<Integer, State>();
		
		ReadMsg readMsg = new ReadMsg(this.ballot);
		for(ActorRef a : this.members) {
			a.tell(readMsg, getSelf());
		}
	}
}






