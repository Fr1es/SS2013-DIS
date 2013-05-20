package de.dis2013.start;

import de.dis2013.database.Database;
import de.dis2013.host.Host;




public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-- Programmstart --");
		
		System.out.println("LSN Page1: "+Database.getInstance().getLsn(1));
		System.out.println("LSN Page2: "+Database.getInstance().getLsn(2));
		System.out.println("DATA Page1: "+Database.getInstance().getData(1));
		System.out.println("DATA Page2: "+Database.getInstance().getData(2));
		
		int a = Host.getInstance().beginTransaction();
		
		Host.getInstance().write(a, 1, "10");
		Host.getInstance().write(a, 2, "20");
		Host.getInstance().write(a, 1, "11");
		Host.getInstance().write(a, 2, "21");
		Host.getInstance().write(a, 2, "22");
		
		Host.getInstance().commit(a);
		
		System.out.println("LSN Page1: "+Database.getInstance().getLsn(1));
		System.out.println("LSN Page2: "+Database.getInstance().getLsn(2));
		System.out.println("DATA Page1: "+Database.getInstance().getData(1));
		System.out.println("DATA Page2: "+Database.getInstance().getData(2));

		System.out.println("-- Programmende --");

	}

}
