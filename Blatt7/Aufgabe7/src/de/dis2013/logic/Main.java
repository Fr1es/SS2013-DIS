package de.dis2013.logic;

public class Main {

	/**
	 * Start here
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(" ----- Application START -----");
		
		//starting the etl-process:
		Etl etl = new Etl();
		etl.clearDWH();
		etl.startFullLoad();
		
		System.out.println(" ----- Application END -----");
	}

}
