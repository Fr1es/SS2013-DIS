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
			
			String shop ="";
			String day ="";
			String article ="";
			boolean exit = false;
			while(true) {
				System.out.println("New query? (y/n)");
				if (reader.readLine().equals("y")) {
					
					//Shop:::
					exit = false;
					do {
						System.out.println("Shop-Dimension:");
						System.out.println("1: Shop; 2: Town; 3: Region; 4: Country");
						input = reader.readLine();
						if (input.equals("1")) {
							shop = "NAME";
							exit = true;
						}
						if (input.equals("2")) {
							shop = "TOWN";
							exit = true;
						}
						if (input.equals("3")) {
							shop = "REGION";
							exit = true;
						}
						if (input.equals("4")) {
//							System.out.println("xxxxxxxxxx");
							shop = "COUNTRY";
							exit = true;
						}
					} while(!exit);
					
					//article:::
					exit = false;
					do {
						System.out.println("Article-Dimension:");
						System.out.println("1: Article; 2: Productgroup; 3: Productfamily; 4: Productcategory");
						input = reader.readLine();
						if (input.equals("1")) {
							article = "NAME";
							exit = true;
						}
						if (input.equals("2")) {
							article = "PRODUCTGROUP";
							exit = true;
						}
						if (input.equals("3")) {
							article = "PRODUCTFAMILY";
							exit = true;
						}
						if (input.equals("4")) {
							article = "PRODUCTCATEGORY";
							exit = true;
						}
					} while(!exit);
					
					//Day:::
					exit = false;
					do {
						System.out.println("Time-Dimension:");
						System.out.println("1: Day; 2: Month; 3: Quarter; 4: Year");
						input = reader.readLine();
						if (input.equals("1")) {
							day = "DAYID";
							exit = true;
						}
						if (input.equals("2")) {
							day = "MONTH";
							exit = true;
						}
						if (input.equals("3")) {
							day = "QUARTER";
							exit = true;
						}
						if (input.equals("4")) {
							day = "YEAR";
							exit = true;
						}
					} while(!exit);

					System.out.println("Sending a query");
					dwh.dwhCube(shop, day, article);
					

					
				} else {
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		 System.out.println(" ----- Application END -----");
	}

}
