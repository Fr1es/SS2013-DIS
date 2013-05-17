package de.dis2013.host.util;

/**
 * Log Sequence Number
 * @author Bjšrn Fries
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
