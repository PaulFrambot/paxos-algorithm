package com.latest;;

public class AckMsg {
	private int ballot;
	
	public AckMsg(int ballot) {
		this.ballot = ballot;
	}

	public int getBallot() {
		return this.ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}
	
}
