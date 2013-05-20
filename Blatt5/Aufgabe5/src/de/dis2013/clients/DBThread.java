package de.dis2013.clients;

import de.dis2013.host.Host;

/**
 * A class used for testing the DB with several clients 
 * @author Björn Fries
 *
 */
public class DBThread  extends Thread{
	private int rangeStart;
	private int time;
	private String name;
	
	/**
	 * Creates a DBThread
	 * RANGE: rangeStart - rangeStart +9!!!
	 * @param rangeStart
	 * @param time how long to wait between the actions
	 * @param name
	 */
	public DBThread(int rangeStart, int time, String name) {
		this.rangeStart = rangeStart;
		this.time = time;
		this.name = name;
	}
	
	/**
	 * Starts an thread run
	 * needs 9 LSNs
	 */
	public void run() {
		int rangeEnd = rangeStart + 9;
		System.out.println("de.dis2013.clients.DBThread - "+this.name+" Range: "+rangeStart+"-"+rangeEnd+" START!");
		
		try {
			Thread.sleep(time);
			int taid = Host.getInstance().beginTransaction();
			
			Thread.sleep(time);
			Host.getInstance().write(taid, rangeStart, "Eintrag1P0");
			
			Thread.sleep(time*2);
			Host.getInstance().write(taid, rangeStart+1, "Eintrag1P1");
			
			Thread.sleep(time);
			Host.getInstance().write(taid, rangeStart+2, "Eintrag1P2");
			
			Thread.sleep(time);
			Host.getInstance().write(taid, rangeStart, "Eintrag2P0");
			
			Thread.sleep(time);
			Host.getInstance().write(taid, rangeStart, "Eintrag3P0");
			
			Thread.sleep(time);
			Host.getInstance().write(taid, rangeStart+2, "Eintrag2P2");
			
			Thread.sleep(time*2);
			Host.getInstance().write(taid, rangeStart+3, "Eintrag1P3");
			
			Thread.sleep(time);
			Host.getInstance().commit(taid);
			
			
		} catch (InterruptedException e) {
			System.out.println("de.dis2013.clients.DBThread - "+this.name+" interrupted END!");
		}
		
		System.out.println("de.dis2013.clients.DBThread - "+this.name+" END!");
	}
}
