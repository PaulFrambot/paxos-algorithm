package com.oldVersion;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.time.Duration;
import java.time.LocalDateTime;


public class Process extends UntypedAbstractActor {
	
    private float crashProbability = (float) 0.2;
    private int proposeFrequency = 100;
    private boolean decided = false;
    private boolean holding = false;

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);// Logger attached to actor
    private final int N;//number of processes
    private final int id;//id of current process
    private Timer timer;
    private Members processes;//other processes' references
    private int proposal;
    private int ballot;
    private int readBallot;
    private int imposeBallot;
    private int estimate;
    private HashMap<ActorRef, Integer[]> states; 
    private String mode;
    private int nACK;
    
    public Process(int ID, int nb) {
        this.N = nb;
        this.id = ID;
        this.timer = timer;
        this.ballot = ID - nb;
        this.proposal = -1; // TODO check si -1 ca va ... dans le cours il met nil -- Si on interpr�te le -1 comme le nil du cours c'est bon. En plus les proposals ne peuvent pas �tre n�gatifs donc on est bon
        this.readBallot = 0;
        this.imposeBallot = ID - nb;
        this.estimate = -1;
        this.states = new HashMap<ActorRef, Integer[]>();
        this.mode = "CORRECT"; // can be CORRECT, FAULTPRONE, SILENT
        this.nACK = 0;
    }
    
    public String toString() {
        return "Process{" + "id=" + id ;
    }

    /**
     * Static function creating actor
     */
    public static Props createActor(int ID, int nb) {
        return Props.create(Process.class, () -> {
            return new Process(ID, nb);
        });
    }
    
    
    private void readReceived(int newBallot, ActorRef pj) {
        if (this.readBallot > newBallot || this.imposeBallot > newBallot) {
        	// Send [abort, ballot'] to pj
        	AbortMsg opm = new AbortMsg(newBallot);
        	pj.tell(opm, this.getSelf());
        	log.info("p" + self().path().name() + " sends ABORT to p" + pj.path().name());
        }
        else {
        	this.readBallot = newBallot;
        	// send ([GATHER, ballot', imposeballot, estimate] to pj
        	log.info("p" + self().path().name() + " sends GATHER to p" + pj.path().name());

        	GatherMsg opm = new GatherMsg(newBallot, this.estimate, this.imposeBallot);
        	opm.setEstimate(this.estimate);
        	opm.setImposeBallot(this.imposeBallot);
        	pj.tell(opm, this.getSelf());
        }
    }
    
    private void gatherReceived(GatherMsg m, ActorRef pj) {
  	  Integer[] arrayReceived = new Integer[2];
  	  arrayReceived[0] = m.getEstimate();
  	  arrayReceived[1] = m.getImposeBallot();
  	  this.states.put(pj, arrayReceived);	
  	  if (this.states.size() >= this.N/2) {
  		  boolean isCorrectEstBallot = false;
  		  int estBallotMax = 0;
  		  int estimate = 0;
  		  for (Entry<ActorRef, Integer[]> entry : this.states.entrySet()) {
  			isCorrectEstBallot = isCorrectEstBallot || (entry.getValue()[1] > 0);
  			estBallotMax = Math.max(estBallotMax, entry.getValue()[1]);
  			if (estBallotMax < entry.getValue()[1]) {
  				estimate = entry.getValue()[0];
  				estBallotMax = entry.getValue()[1];
  			}
  		}
  		if (isCorrectEstBallot) {
  			this.proposal = estimate;
  		}
  		this.states = new HashMap<ActorRef, Integer[]>();
  		
  		// send [IMPOSE, ballot, proposal] to all
  		log.info("p" + self().path().name() + " sends IMPOSE to all");
  		ImposeMsg opm = new ImposeMsg(this.ballot, this.proposal);
  		for (ActorRef actor : processes.references) {
            actor.tell(opm, this.getSelf());
        }
  	  }
    }
    
    private void imposeReceived(ImposeMsg m, ActorRef pj) {
    	if (this.readBallot > m.getBallot() || this.imposeBallot > m.getBallot()) {
    		// Send [abort, ballot'] to pj
        	log.info("p" + self().path().name() + " sends ABORT p" + pj.path().name());
        	AbortMsg opm = new AbortMsg(m.getBallot());
        	pj.tell(opm, this.getSelf());
    	}
    	else {
    		this.proposal = m.getProposal();
    		this.imposeBallot = m.getBallot();
    		// send [ACK, ballot'] to pj
    		log.info("p" + self().path().name() + " sends ACK to p" + pj.path().name());
    		AckMsg opm = new AckMsg(m.getBallot());
        	pj.tell(opm, this.getSelf());
    	}
    }
    
    private void ackReceived(AckMsg m) {

    	if (m.getBallot() == this.imposeBallot) {
    		this.nACK =this.nACK + 1;
    	}
    	
    	if (this.nACK > this.N/2) {
    		// send [DECIDE, proposal] to all
    		log.info("p" +	 self().path().name() + " sends DECIDE to all");
    		DecideMsg opm = new DecideMsg(this.ballot, this.proposal);
        	
    		if (!Timer.getUsed()) {
    			LocalDateTime start = Timer.getStart();
	        	LocalDateTime end = LocalDateTime.now();  
	        	Duration duration = Duration.between(start, end);
	        	log.info("DURATION: " + duration);

	        	Timer.setUsed(false);
    		}
      		for (ActorRef actor : processes.references) {
                actor.tell(opm, this.getSelf());
      		}
      		this.nACK = 0;
    	}
    }
    
    private void decideReceived(DecideMsg m) {
    	// send [DECIDE, proposal] to all
  		log.info("p" + self().path().name() + " sends [DECIDE, proposal] to all");
  		DecideMsg opm = new DecideMsg(this.ballot, this.proposal);
  		for (ActorRef actor : processes.references) {
            actor.tell(opm, this.getSelf());
  		
    	}
  		decided=true;
  		
    }

    private void crashReceived() {
    	this.mode = "FAULTPRONE";
        log.info("Crash message received : FAULTPRONE mode activated for p" + self().path().name() );
    }
   
  
    
    public void onReceive(Object message) throws Throwable {
    	// log.info("Mode of p"+ self().path().name() +" : "+this.mode );
    	  ActorRef pj = getSender();
    	  if (this.mode != "SILENT" ) {
    		  if (this.mode == "FAULTPRONE") {
        		  // Trigger a Possible Crash
			      if ((float)Math.random() <= this.crashProbability) {
				      this.mode = "SILENT";
				      log.info("p"+self().path().name()+ " has turned to SILENT mode");
				  }	
    		  }
    		  
    		  // Premier message reçu : Stock les références des autres acteurs
    		  if (message instanceof Members && this.mode != "SILENT") { 
                  Members m = (Members) message;
                  processes = m;
                  log.info("p" + self().path().name() + " received processes info");
              }
    		  else if(message instanceof ReadMsg  && this.mode != "SILENT") {
    			  ReadMsg m = (ReadMsg) message;
    			  log.info("p" + self().path().name() + " received READ from p" + getSender().path().name());
            	  this.readReceived(m.getBallot(), pj);
    		  } 
    		  else if (message instanceof GatherMsg) {
    			  GatherMsg m = (GatherMsg) message;
    			  log.info("p" + self().path().name() + " received GATHER from p" + getSender().path().name());
            	  this.gatherReceived(m, pj);
    		  }
    		  else if(message instanceof AbortMsg) {
    			  log.info("p" + self().path().name() + " received ABORT from p" + getSender().path().name());
            	  // TODO : return abort
    		  } 
    		  else if (message instanceof ImposeMsg) {
    			  ImposeMsg m = (ImposeMsg) message;
    			  log.info("p" + self().path().name() + " received IMPOSE from p" + getSender().path().name());
            	  this.imposeReceived(m, pj);
    		  }
    		  else if(message instanceof AckMsg) {
    			  AckMsg m = (AckMsg) message;
    			  log.info("p" + self().path().name() + " received ACK from p" + getSender().path().name());
            	  this.ackReceived(m);
    		  }
    		  else if (message instanceof DecideMsg) {
    			  DecideMsg m = (DecideMsg) message;
    			  if (!decided) {
            		  log.info("p" + self().path().name() + " received DECIDE from p" + getSender().path().name()+ " and decided : " + this.proposal);
                	  this.decideReceived(m);
            	  }      
    		  }
              else if (message instanceof LaunchMsg  && this.mode != "SILENT") {
            	  LaunchMsg m = (LaunchMsg) message;
                  if  (!decided && !holding) {
                	  this.timer = m.getTimer();
                      log.info("Launch Message received by p" + self().path().name() );
                      this.propose(m.getProposal());
                  	  this.transferToScheduler(m, this.proposeFrequency);
                  }

              }
              else if (message instanceof CrashMsg  && this.mode != "SILENT") {
                  this.crashReceived();
              }
              else if (message instanceof HoldMsg  && this.mode != "SILENT") {
                  log.info("HOLD Message received by p" + self().path().name() );
            	  this.holding = true;            	  
              }
    	 } 
    }
    

	private void propose(int proposition) { // proposition = 0 ou 1

		// Update proposal, ballot and states
    	this.proposal = proposition;
    	this.ballot += this.N;
    	this.states = new HashMap<ActorRef, Integer[]>();
    	this.nACK = 0;
    	// Send message to all processes
    	ReadMsg opm = new ReadMsg(this.ballot);
    	log.info("p" + self().path().name() + " proposes :"+ proposition +" he asks everybody's opinion sending READ"+processes.references);
    	

    	for (ActorRef actor : processes.references) {
            actor.tell(opm, this.getSelf());
        }
    }
	
	
	public void transferToScheduler(LaunchMsg message, int proposeFrequency) {		
		// il faut recréer le message pour changer le sender
		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(proposeFrequency), getSelf(), new LaunchMsg(message.getProposal(), timer), getContext().system().dispatcher(), ActorRef.noSender());
	}
}
