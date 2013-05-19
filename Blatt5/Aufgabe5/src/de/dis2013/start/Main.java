package de.dis2013.start;

import de.dis2013.host.Host;




public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-- Programmstart --");
		
		int a = Host.getInsatnce().beginTransaction();
		
		Host.getInsatnce().write(a, 1, "10");
		Host.getInsatnce().write(a, 2, "20");
		Host.getInsatnce().write(a, 1, "10");
		Host.getInsatnce().write(a, 2, "20");
		Host.getInsatnce().write(a, 2, "20");
		
		Host.getInsatnce().commit(a);

		System.out.println("-- Programmende --");

	}

}
