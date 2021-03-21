package com.oldVersion;



import akka.actor.Props;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Scheduler extends AbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Scheduler() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Scheduler.class, () -> {
			return new Scheduler();
		});
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
			.match(LaunchMsg.class, this::receiveFunction)
			.build();
	  }

	public void receiveFunction(LaunchMsg m){
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"] with data");

		getSender().tell(m, getSelf());
	}
}


