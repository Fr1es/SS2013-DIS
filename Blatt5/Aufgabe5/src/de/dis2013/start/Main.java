package de.dis2013.start;

import de.dis2013.host.Buffer;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-- Programmstart --");
		
		Buffer.getInstance().addBufferEntry(1, 1, 1, "10", false);
		Buffer.getInstance().addBufferEntry(2, 1, 2, "10", false);
		Buffer.getInstance().addBufferEntry(3, 1, 2, "10", false);
		Buffer.getInstance().addBufferEntry(4, 1, 1, "20", false);
//		Buffer.getInstance().addBufferEntry(5, 2, 1, "999", false);
		Buffer.getInstance().addBufferEntry(6, 1, 1, "10", false);
		Buffer.getInstance().addBufferEntry(7, 2, 1, "test", false);
		Buffer.getInstance().addBufferEntry(8, 2, -99, "", true);
		//neue funktion: buffer commit(int taid) suche alle pages mit der taid und setze sie auf commit
		

		System.out.println("-- Programmende --");

	}

}
