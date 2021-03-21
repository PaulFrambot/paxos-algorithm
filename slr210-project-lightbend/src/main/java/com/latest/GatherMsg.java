package com.latest;;

public class GatherMsg {
	private int id;
	private int ballot;
	private int imposeballot;
	private int estimate;
	
	public GatherMsg(int id, int ballot, int imposeBallot, int estimate) {
		this.id = id;
		this.ballot = ballot;
		this.imposeballot = imposeBallot;
		this.estimate = estimate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBallot() {
		return this.ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}

	public int getImposeballot() {
		return this.imposeballot;
	}

	public void setImposeballot(int imposeballot) {
		this.imposeballot = imposeballot;
	}

	public int getEstimate() {
		return this.estimate;
	}

	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}
	
}
