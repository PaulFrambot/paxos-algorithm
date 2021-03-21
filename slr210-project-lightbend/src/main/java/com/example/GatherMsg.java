package com.example;;

public class GatherMsg {
	private int id;
	private int ballot;
	private int imposeballot;
	private int estimate;
	
	public GatherMsg(int id_, int b, int i, int e) {
		id = id_;
		ballot = b;
		imposeballot = i;
		estimate = e;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBallot() {
		return ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}

	public int getImposeballot() {
		return imposeballot;
	}

	public void setImposeballot(int imposeballot) {
		this.imposeballot = imposeballot;
	}

	public int getEstimate() {
		return estimate;
	}

	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}
	
}
