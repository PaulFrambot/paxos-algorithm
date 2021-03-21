package com.latest;;

public class ImposeMsg {
	private int ballot;
	private int proposal;
	
	public ImposeMsg(int ballot, int proposal) {
		this.ballot = ballot;
		this.proposal = proposal;
	}

	public int getBallot() {
		return this.ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}

	public int getProposal() {
		return this.proposal;
	}

	public void setProposal(int proposal) {
		this.proposal = proposal;
	}
	
}
