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
	private int readballot; // read ballot number
	private int imposeballot; // impose ballot number
	private int estimate;  // estimate proposed value
	private int nbAbortRound1;
	private int nbAbortRound2;
	private HashMap<Integer, State> states; // states
	private HashMap<Integer, Integer> acks; // acks
	private int initialProposal; 
	private boolean decided; 
	private boolean faulty_prone_mode;
	private boolean silent_mode;
	private boolean hold;
	private double crashProba;
	private long startTime;

	public Process(int i, int n) {
		N = n; id = i;
		decided = false;
		ballot = id - N; readballot = 0; imposeballot = id - N;
		estimate = -1; proposal = -1; nbAbortRound1 = 0; nbAbortRound2 = 0;
		faulty_prone_mode = false;
		silent_mode = false;
		hold = false;
		crashProba = 0;
		startTime = 0;
		members = new ArrayList<ActorRef>();
		states = new HashMap<Integer, State>();
		acks = new HashMap<Integer, Integer>();
	}
	
	public static Props createProcess(int i, int n) {
		return Props.create(Process.class, () -> {
			return new Process(i, n);
		});
	}
	
	
	@Override
	public void onReceive(Object message) throws Throwable {
		if(!silent_mode && faulty_prone_mode) {
			if (Math.random() < crashProba) {
				silent_mode = true;
			}
		}
		else if(silent_mode) return; 
		
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
			launch(m);
		}
		
		else if(message instanceof Timer) {
			Timer m = (Timer) message;
			startTime = m.getStart();
		}
		
		else if(message instanceof ReadMsg) {
//			log.info("p" + self().path().name() + " received READ from p" + getSender().path().name());
			ReadMsg m = (ReadMsg) message;
			read(m);
		}
		
		else if(message instanceof AbortMsg) {
//			log.info("p" + self().path().name() + " received ABORT from p" + getSender().path().name());
			AbortMsg m = (AbortMsg) message;
			abort(m);
		}
		
		else if(message instanceof GatherMsg) {
//			log.info("p" + self().path().name() + " received GATHER from p" + getSender().path().name());
			GatherMsg m = (GatherMsg) message;
			gather(m);
		}
		
		else if(message instanceof ImposeMsg) {
//			log.info("p" + self().path().name() + " received IMPOSE from p" + getSender().path().name());
			ImposeMsg m = (ImposeMsg) message;
			impose(m);
		}
		
		else if(message instanceof AckMsg) {
//			  log.info("p" + self().path().name() + " received ACK from p" + getSender().path().name());

			AckMsg m = (AckMsg) message;
			ack(m);
		}
		
		else if(message instanceof DecideMsg) {
//			log.info("p" + self().path().name() + " received DECIDE from p" + getSender().path().name());
			DecideMsg m = (DecideMsg) message;
			decide(m);
		}	
	}
	
	public void receiveMembers(Members m) {
		members = m.members;
	}
	
	public void crash(CrashMsg msg) {
		
		faulty_prone_mode = true;
		crashProba = msg.getCrashProba();
	}
	
	public void launch(LaunchMsg s) {		
		initialProposal = s.getProposal();
		propose(s.getProposal());
	}
	
	public void propose(int p) {		
		ballot += N; proposal = p; acks.put(ballot, 0);
		nbAbortRound1 = 0; nbAbortRound2 = 0; 
		states = new HashMap<Integer, State>();
		
		ReadMsg readMsg = new ReadMsg(ballot);
		for(ActorRef a : members) {
			a.tell(readMsg, getSelf());
		}
	}
	
	
	public void read(ReadMsg msg) {
		int ballotNumber = msg.getBallot();
		if (readballot > ballotNumber || imposeballot > ballotNumber) {
			AbortMsg abortMessage = new AbortMsg(ballotNumber);
			abortMessage.setRound(1);
			getSender().tell(abortMessage, getSelf());
		}
		
		else {
			readballot = ballotNumber;
			getSender().tell(new GatherMsg(id, ballotNumber, imposeballot, estimate), getSelf());
		}
	}
	
	public void abort(AbortMsg msg) {
		// on distingue les différents abort et si il y a une majorité on repropose
		if(msg.getRound() == 1 ) nbAbortRound1++;
		if(msg.getRound() == 2) nbAbortRound2++;
		boolean majorityReached = ((nbAbortRound1++ > N/2 && (msg.getRound() == 1) || (nbAbortRound2++ > N/2 && (msg.getRound() == 2))));
		if(!hold && !decided && majorityReached) getSelf().tell(new LaunchMsg(initialProposal), getSelf()); 
		// remise des compteurs à 0 quand on a atteint une majorité
		if(nbAbortRound1++ > N/2) nbAbortRound1 = 0;
		if(nbAbortRound2++ > N/2) nbAbortRound2 = 0;
		return;
	}
	
	public void gather(GatherMsg msg) {
		states.put(msg.getId(), new State(msg.getEstimate(), msg.getBallot()));
		nbAbortRound1++;
		if(states.size() > N/2) {
			int max_est_ballot = 0;
			int est = 0;
			for(int i : states.keySet()) {
				int b = states.get(i).ballot;
				if(b > max_est_ballot) {
					max_est_ballot = b;
					est = states.get(i).est;
				}
			}
			
			if(max_est_ballot > 0 && est > -1) proposal = est;
			
			states = new HashMap<Integer, State>();
			
			ImposeMsg imp = new ImposeMsg(ballot, proposal);
			for(ActorRef a : members) {
				a.tell(imp, getSelf());
			}
		}
	}
	
	public void impose(ImposeMsg msg) {
		int b = msg.getBallot();
		if(readballot > b || imposeballot > b) {
			getSender().tell(new AbortMsg(b), getSelf());
		}
		
		else {
			estimate = msg.getProposal();
			imposeballot = b;
			getSender().tell(new AckMsg(b), getSelf());
		}
	}
	
	public void ack(AckMsg msg) {
		nbAbortRound2++;
		int nb = acks.get(msg.getBallot()) + 1;
		acks.put(msg.getBallot(), nb);
		for(int i : acks.keySet()) {
			if (acks.get(i) > N/2) {
				DecideMsg dec = new DecideMsg(proposal);
				for(ActorRef a : members) {
					a.tell(dec, getSelf());
				}
				break;
			}
		}
	}
	
	public void decide(DecideMsg msg) {
		if(!decided) {
			long endTime = System.currentTimeMillis();
			log.info("p" + Integer.toString(id) + " decides " + Double.toString(msg.getProposal()) + " in " + Long.toString(endTime - startTime) + "ms");
			for(ActorRef a : members) {
				a.tell(msg, getSelf());
			}
		}
		decided = true;
	}
}






