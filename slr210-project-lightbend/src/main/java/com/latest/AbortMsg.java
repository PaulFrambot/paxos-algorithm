package com.latest;;

public class AbortMsg {
	private int ballot;
	private boolean isReadAbort; // 1 if it is aborting after read or 2 if after impose
	
	public AbortMsg(int ballot, boolean isReadAbort) {
		this.ballot = ballot;
		this.isReadAbort = isReadAbort;
	}

	public int getBallot() {
		return ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}

	public boolean getIsReadAbortd() {
		return this.isReadAbort;
	}

	public void setRound(boolean isReadAbort) {
		this.isReadAbort = isReadAbort;
	}


	
}
