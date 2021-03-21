package com.oldVersion;

public class GatherMsg {	
	private int ballot;
	private int imposeBallot;
	private int estimate;
	
	public GatherMsg(int ballot, int estimate, int imposeBallot) {
		this.ballot = ballot;
		this.imposeBallot = imposeBallot;
		this.estimate = estimate;
	}

	public int getBallot() {
		return ballot;
	}


	public void setBallot(int ballot) {
		this.ballot = ballot;
	}
	
	public int getImposeBallot() {
		return this.imposeBallot;
	}


	public void setImposeBallot(int imposeBallot) {
		this.imposeBallot = imposeBallot;
	}
	
	public int getEstimate() {
		return this.estimate;
	}


	public void setEstimate(int estimate) {
		this.imposeBallot = estimate;
	}

		
}
