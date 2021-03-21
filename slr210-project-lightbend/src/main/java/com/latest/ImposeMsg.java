package com.latest;;

public class ImposeMsg {
	private int ballot;
	private int proposal;
	
	public ImposeMsg(int b, int p) {
		ballot = b;
		proposal = p;
	}

	public int getBallot() {
		return ballot;
	}

	public void setBallot(int ballot) {
		this.ballot = ballot;
	}

	public int getProposal() {
		return proposal;
	}

	public void setProposal(int proposal) {
		this.proposal = proposal;
	}
	
}
