package com.latest;;

public class AbortMsg {
	private int ballot;
	private int round; // 1 if it is aborting after read or 2 if after impose
	
	public AbortMsg(int b) {
		ballot = b;
		round = 2;
	}

	public int getBallot() {
		return ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}


	
}
