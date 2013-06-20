package de.dis2013.logic;

import java.sql.Connection;

import de.dis2013.db.DWHConnectionManager;
import de.dis2013.db.STAMMDATENConnectionManager;

/**
 * Starts the ETL-Process
 * @author Björn Fries
 *
 */
public class Etl {
	private final Connection DWH;
	private final Connection STAMM;
	
	public Etl() {
		// Build Connections:
		DWH = DWHConnectionManager.getInstance().getConnection();
		STAMM = STAMMDATENConnectionManager.getInstance().getConnection();
	}
	
	/**
	 * Starts the ETL-process (FULL load!)
	 */
	public void startFullLoad() {
		System.out.println(" -- de.dis2013.logic.Etl START full load --");
		
		
		System.out.println(" -- de.dis.2013.logic.Etl END full load --");
	}
	
	/**
	 * Clears the whole DWH (should be done before a full loads starts
	 */
	public void clearDWH() {
		System.out.println("de.dis.2013.logic.Etl - clearing DWH start");
		
		
		System.out.println("de.dis.2013.logic.Etl - clearing DWH end");
	}

}
