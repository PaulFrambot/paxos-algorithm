package com.latest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Random;
import java.util.ArrayList;

import java.util.Collections;

public class Main {
    
    public static void main(String[] args) {
	
	final int N = 100;
	final int f = 49;
	final long tle = 2000;
	
	final ActorSystem system = ActorSystem.create("system");
	
    final ArrayList<ActorRef> members = new ArrayList<ActorRef>();
    
    try {
 
		for(int x = 0; x < N; x += 1) {
		     members.add(system.actorOf(Process.createProcess(x, N), Integer.toString(x)));
		}    
	
	    for(int x = 0; x < N; x += 1) {
	    	members.get(x).tell(new Members(members), ActorRef.noSender());
		}
	    
	    Collections.shuffle(members);
	    for(int x = 0; x < f; x += 1) {
	    	members.get(x).tell(new CrashMsg(Math.random()), ActorRef.noSender());
	    }
	    
	    final long startTime = System.currentTimeMillis();
	    
	    int proposal = -1;
	    for(int x = 0; x < N; x += 1) {
	    	double ran = Math.random();
	    	if (ran >= 0.5) {
	    		proposal = 1;
	    	} 
	    	else if (ran < 0.5 ) {
	    		proposal = 0;
	    	}
	    	members.get(x).tell(new LaunchMsg(proposal), ActorRef.noSender());
	    	members.get(x).tell(new Timer(startTime), ActorRef.noSender());
	    }
	    
	    Thread.sleep(tle);
	    int leader_id = (int)(Math.random() * (N - f)) + f;
	    for(int x = f; x < N; x += 1) {
	    	if(x != leader_id) members.get(x).tell(new HoldMsg(), ActorRef.noSender());
	    }
	    
    } catch (Exception ioe) {} 
    
    finally { 
	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
    } 
  }
    
	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(10000);
	}
    
}
