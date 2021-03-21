package com.oldVersion;
import java.time.LocalDateTime;    

public class Timer {
	private static LocalDateTime start;
	private static boolean used;

	public Timer(LocalDateTime start, boolean used) {
		this.start = start;
		this.used = used;

	}

	public static LocalDateTime getStart() {
		return start;
	}

	public static void setStart(LocalDateTime start) {
		start = start;
	}
	
	public static boolean getUsed() {
		return used;
	}


	public static void setUsed(boolean used) {
		used = used;
	}	
}
