package com.latest;;

public class AckMsg {
	private int ballot;
	
	public AckMsg(int b) {
		ballot = b;
	}

	public int getBallot() {
		return ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}
	
}
