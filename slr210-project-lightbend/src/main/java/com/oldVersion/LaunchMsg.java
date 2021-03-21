package com.oldVersion;

public class LaunchMsg {
	private int proposal;
	private Timer timer;
	
	public LaunchMsg(int d, Timer timer) {
		this.setProposal(d);
		this.timer = timer;
	}
	public int getProposal() {
		return proposal;
	}
	public void setProposal(int proposal) {
		this.proposal = proposal;
	}
	public Timer getTimer() {
		return timer;
	}
}
