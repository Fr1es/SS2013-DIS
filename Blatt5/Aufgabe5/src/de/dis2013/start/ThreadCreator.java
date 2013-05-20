package de.dis2013.start;

import de.dis2013.clients.DBThread;

/**
 * A class for calling several DBThreads
 * @author Björn Fries
 *
 */
public class ThreadCreator {
	private int numberThreads;
	
	public ThreadCreator (int numberThreads) {
		this.numberThreads = numberThreads;
	}
	
	public void startThreads() {
		DBThread[] threads = new DBThread[numberThreads]; 
		
		//initial:
		for (int i = 1; i<= threads.length; i++) {
			threads[i-1] = new DBThread(i*10, i*300, "Thread"+i);		
			threads[i-1].start();		
			}
	}

}
