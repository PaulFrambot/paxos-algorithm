package com.oldVersion;


public class DecideMsg {
	private int ballot;
	private int proposal;
	
	public DecideMsg(int ballot, int proposal) {
		this.ballot = ballot;
		this.proposal = proposal;
	}

	public int getBallot() {
		return ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}
	
	public int getProposal() {
		return this.proposal;
	}


	public void setProposal(int proposal2) {
		this.proposal = proposal2;
	}
		
}
