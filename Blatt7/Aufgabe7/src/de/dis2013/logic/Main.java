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
		
		try {
			// for reading stuff
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Start Full-Load? (y/n)");
			String input = reader.readLine();
			
			if (input.equals("y")) {
				System.out.println("Starting Full-Load as requested from user");
				//starting the etl-process:
				Etl etl = new Etl();
				etl.clearDWH();
				etl.startFullLoad();
			} else {
				System.out.println("No Full-Load will be executed");
			}
			
			System.out.println();
			System.out.println("-- Starting the Reporting-Tool for the DWH --");
			DWH dwh = new DWH();
			dwh.dwhCube("NAME", "QUARTER", "PRODUCTFAMILY");
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		 System.out.println(" ----- Application END -----");
	}

}
