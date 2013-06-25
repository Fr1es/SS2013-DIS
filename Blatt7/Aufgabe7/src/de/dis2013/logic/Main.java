package de.dis2013.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		
		DWH dwh = new DWH();
		
		System.out.println(dwh);
		
		// for reading stuff
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.println(" Which attribute to use? (place, time, product)");

			String attributeName = null;
			try {
				attributeName = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(" Use drill down (0) or roll up (1)?");
			int action = -1;
			try {
				action = Integer.parseInt(reader.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (action == 0) {
				dwh.drillDown(attributeName);
			} else if (action == 1) {
				dwh.rollUp(attributeName);
			}
			
			System.out.println(dwh);
		}
		
		// System.out.println(" ----- Application END -----");
	}

}
