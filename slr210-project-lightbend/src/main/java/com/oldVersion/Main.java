package com.oldVersion;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;
import java.time.LocalDateTime;    


public class Main {

    public static int N = 100;
    public static int f = 49;
    public static int timeout = 500;
    
    public static void main(String[] args) throws InterruptedException {

        // Instantiate an actor system
        final ActorSystem system = ActorSystem.create("system");
        system.log().info("System started with N=" + N );

        ArrayList<ActorRef> references = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            // Instantiate processes
            final ActorRef a = system.actorOf(Process.createActor(i + 1, N), "" + i);
            references.add(a);
        }

        // Give each process a view of all the other processes
        Members m = new Members(references);
        for (ActorRef actor : references) {
            actor.tell(m, ActorRef.noSender());
        }
        
    
        // f process can be faulty
        CrashMsg crashMessage = new CrashMsg();
        Collections.shuffle(references);
        for (int i=0; i<f; i++) {
        	 references.get(i).tell(crashMessage, ActorRef.noSender());
        }
        
        
        
        // For every process, the main method then sends a special launch message. 
       
    	int proposition = 0;
        for (ActorRef actor : references) {
        	proposition = 0;
        	double random = Math.random();
        	if (random > (double) 0.5) {
        		proposition = 1;
        	}
        	Timer timer = new Timer(LocalDateTime.now(), false);
        	LaunchMsg specialLaunchMessage = new LaunchMsg(proposition, timer);
   
//        	system.log().info("DURATION: " + timer.getStart());

            actor.tell(specialLaunchMessage, ActorRef.noSender());
        }       
        
        Thread.sleep(timeout);
        // Le Nième process n'est pas faultprone donc on ne lui envoye pas le hold
        HoldMsg holdMessage = new HoldMsg();
        for (int i=0; i<N-1; i++) {
       	 	references.get(i).tell(holdMessage, ActorRef.noSender());
       }
        system.log().info("LEADER is p" + references.get(N-1).path().name());
        
        
    }
}
