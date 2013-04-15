package de.dis2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Kleine Helferklasse fuer Menues
 * Zuvor muessen mit addEntry Menueoptionen hinzugefuegt werden. Mit
 * der Methode show() wird das Menue angezeigt und die mit der Option
 * angegebene Konstante zurueckgeliefert.
 * 
 * Beispiel:
 * Menu m = new Menu("Hauptmenue");
 * m.addEntry("Hart arbeiten", 0);
 * m.addEntry("Ausruhen", 1);
 * m.addEntry("Nach Hause gehen", 2);
 * int wahl = m.show();
 * 
 * Angezeigt wird dann das Menue:
 * Hauptmenue:
 * [1] Hart arbeiten
 * [2] Ausruhen
 * [3] Nach Hause gehen
 * --
 */
public class Menu {
	private String title;
	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<Integer> returnValues = new ArrayList<Integer>();
	
	/**
	 * Konstruktor.
	 * @param title Titel des Menues z.B. "Hauptmenue"
	 */
	public Menu(String title) {
		super();
		this.title = title;
	}
	
	/**
	 * Fuegt einen Menueeintrag zum Menue hinzu
	 * @param label Name des Eintrags
	 * @param returnValue Konstante, die bei Wahl dieses Eintrags zurueckgegeben wird
	 */
	public void addEntry(String label, int returnValue) {
		this.labels.add(label);
		this.returnValues.add(new Integer(returnValue));
	}
	
	/**
	 * Zeigt das Menue an
	 * @return Die Konstante des ausgewaehlten Menueeintrags
	 */
	public int show()  {
		int selection = -1;
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		while(selection == -1) {
			System.out.println(title+":");
			
			for(int i = 0; i < labels.size(); ++i) {
				System.out.println("["+(i+1)+"] "+labels.get(i));
			}
			
			System.out.print("-- ");
			try {
				selection = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(selection < 1 || selection > returnValues.size()) {
				System.err.println("Ungueltige Eingabe!");
				selection = -1;
			} 
		}
		
		return returnValues.get(selection-1);
	}
	
	public boolean password(String korrekt){
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String eingabe = "";
		
		
		System.out.println("Bitte geben Sie Ihr Passwort an.");
		
		for (int i=1; i<=3; i++)
		{
			System.out.print("Passwort: ");
			try {
				eingabe = stdin.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (eingabe.equals(korrekt)) {
				System.out.println("Passwort ist korrekt.");
				return true;
			}
			else {
				System.out.println("Passwort ist NICHT korrekt.");
			}
			
		}
		
		return false;
	}
}
