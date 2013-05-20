package de.dis2013.start;

import de.dis2013.database.Database;
import de.dis2013.host.Host;




public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-- Programmstart --");
		
		System.out.println("LSN1: "+Database.getInstance().getLsn(1));
		System.out.println("LSN2: "+Database.getInstance().getLsn(2));
		System.out.println("DATA1: "+Database.getInstance().getData(1));
		System.out.println("DATA2: "+Database.getInstance().getData(2));
		
		int a = Host.getInstance().beginTransaction();
		
		Host.getInstance().write(a, 1, "10");
		Host.getInstance().write(a, 2, "20");
		Host.getInstance().write(a, 1, "10");
		Host.getInstance().write(a, 2, "20");
		Host.getInstance().write(a, 2, "20");
		
		Host.getInstance().commit(a);
		
		System.out.println("LSN1: "+Database.getInstance().getLsn(1));
		System.out.println("LSN2: "+Database.getInstance().getLsn(2));
		System.out.println("DATA1: "+Database.getInstance().getData(1));
		System.out.println("DATA2: "+Database.getInstance().getData(2));

		System.out.println("-- Programmende --");

	}

}
