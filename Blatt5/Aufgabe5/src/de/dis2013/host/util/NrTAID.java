package de.dis2013.host.util;

/**
 * transaction ID
 * @author Björn Fries
 *
 */
public class NrTAID {
	private int taid = 0;
	
	public synchronized void increment() {
		taid++;
	}
	
	public synchronized void decrement() {
		taid--;
	}
	
	public synchronized int get() {
		return taid;
	}
	
	public synchronized void set(int newValue) {
		taid = newValue;
	}

}
