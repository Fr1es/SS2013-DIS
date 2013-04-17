package de.dis2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Kleine Helferklasse zum Einlesen von Formulardaten
 */
public class FormUtil {
	/**
	 * Liest einen String vom standard input ein
	 * @param label Zeile, die vor der Eingabe gezeigt wird
	 * @return eingelesene Zeile
	 */
	public static String readString(String label) {
		String ret = null;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.print(label+": ");
			ret = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * Liest einen Integer vom standard input ein
	 * @param label Zeile, die vor der Eingabe gezeigt wird
	 * @return eingelesener Integer
	 */
	public static int readInt(String label) {
		int ret = 0;
		boolean finished = false;

		while(!finished) {
			String line = readString(label);
			
			try {
				ret = Integer.parseInt(line);
				finished = true;
			} catch (NumberFormatException e) {
				System.err.println("Ungültige Eingabe: Bitte geben Sie eine Zahl an!");
			}
		}
		
		return ret;
	}
	
	/**
	 * Liest nur einen Char ein, der entweder Y oder N ist.
	 */
	public static char readChar(String label) {
		char ret = 'A';
		boolean finished = false;

		while(!finished) {
			String line = readString(label+" (Y/N)").toUpperCase();
			
			if (line.length() == 1 && 
					( line.equals("N") || line.equals("Y") ) ) {
				ret = line.charAt(0);
				finished = true;
			} 
			else {
				System.err.println("Ungültige Eingabe: Bitte geben Sie nur 'N' oder 'Y' an!");
			}

		}
		
		return ret;
	}
	
	/**
	 * Liest einen Double vom standard input ein (gerundet auf 4 Nachkommastellen)
	 */
	public static double readDouble(String label) {
		double ret = 0;
		boolean finished = false;

		while(!finished) {
			String line = readString(label);
			
			try {
				ret = Double.parseDouble(line);
				ret = Math.round(ret*10000)/10000.0;	
				
				finished = true;
			} catch (NumberFormatException e) {
				System.err.println("Ungültige Eingabe: Bitte geben Sie eine Gleitkommazahl an!");
			}
		}
		
		return ret;
	}
	
	/**
	 * Liest einen Double vom standard input ein (gerundet auf 2 Nachkommastellen)
	 */
	public static double readPreis(String label) {
		double ret = 0;
		boolean finished = false;

		while(!finished) {
			String line = readString(label);
			
			try {
				ret = Double.parseDouble(line);
				ret = Math.round(ret*100)/100.0;	
				
				finished = true;
			} catch (NumberFormatException e) {
				System.err.println("Ungültige Eingabe: Bitte geben Sie eine Gleitkommazahl mit 2 Nachkommastellen an!");
			}
		}
		
		return ret;
	}
}
