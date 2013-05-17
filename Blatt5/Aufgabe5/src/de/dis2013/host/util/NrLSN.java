package de.dis2013.host.util;

/**
 * log sequence number
 * @author Björn Fries
 *
 */
public class NrLSN {
	private int lsn = 0;
	
	public synchronized void increment() {
		lsn++;
	}
	
	public synchronized void decrement() {
		lsn--;
	}
	
	public synchronized int get() {
		return lsn;
	}
	
	public synchronized void set(int newValue) {
		lsn = newValue;
	}

}
