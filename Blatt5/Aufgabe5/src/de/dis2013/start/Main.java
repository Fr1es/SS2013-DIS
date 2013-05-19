package de.dis2013.start;

import de.dis2013.host.Host;




public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-- Programmstart --");
		
		int a = Host.getInstance().beginTransaction();
		
		Host.getInstance().write(a, 1, "10");
		Host.getInstance().write(a, 2, "20");
		Host.getInstance().write(a, 1, "10");
		Host.getInstance().write(a, 2, "20");
		Host.getInstance().write(a, 2, "20");
		
		Host.getInstance().commit(a);

		System.out.println("-- Programmende --");

	}

}
