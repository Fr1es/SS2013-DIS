package de.dis2011;

import de.dis2011.data.Kaufvertrag;
import de.dis2011.data.Makler;
import de.dis2011.data.Mietvertrag;
import de.dis2011.data.Person;
import de.dis2011.data.Wohnung;

/**
 * Hauptklasse
 */
public class Main {
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}
	
	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_MAKLER = 0;
		final int IMMOBILIEN = 1;
		final int VERTRAG = 2;
		final int QUIT = 3;
		
		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
		mainMenu.addEntry("Immobilien-Verwaltung", IMMOBILIEN);
		mainMenu.addEntry("Vertragsmodus", VERTRAG);
		mainMenu.addEntry("Beenden", QUIT);
		
		//Verarbeite Eingabe
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_MAKLER:
					showMaklerMenu();
					break;
				case IMMOBILIEN:
					showImmobilienMenu();
					break;
				case VERTRAG:
					showVertragMenu();
					break;
				case QUIT:
					return;
			}
		}
	}
	
	/**
	 * Zeigt die Maklerverwaltung
	 */
	public static void showMaklerMenu() {
		//Menüoptionen
		final int NEW_MAKLER = 0;
		final int DELETE = 1;
		final int BACK = 2;
		
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Verwaltung");
		
		if (!maklerMenu.password("admin")) { //Passwortabfrage
			return;
		}
		
		maklerMenu.addEntry("Neuer Makler / Makler aendern", NEW_MAKLER);
		maklerMenu.addEntry("Makler löschen", DELETE);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_MAKLER:
					newMakler(); // ok
					break;
				case DELETE:
					deleteMakler(); // ok
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Legt einen neuen Makler an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newMakler() {
		Makler m = new Makler();
		
		System.out.println("Geben Sie bitte die Daten eines Maklers ein, die gespeichert werden sollen." +
				"\nJeder Login wird nur einmal vergeben");
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adresse"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();
	}
	
	public static void deleteMakler() {
		Makler m = new Makler();
		
		System.out.println("Geben Sie bitte den Login des Maklers ein, der gelöscht werden soll.");
		m.delete(FormUtil.readString("Login"));
	}


public static void showImmobilienMenu() {
	//Maklerlogin zuerst!
	Makler m = new Makler();
	if (!m.loginMakler()) {
		System.out.println("Ungueltiger Loginversuch.");
		return;
	}
	
	
	//Menüoptionen
	final int NEWHOUSE = 0;
	final int NEWFLAT = 1;
	final int DELETEHOUSE = 2;
	final int DELETEFLAT = 3;
	final int BACK = 4;
	
	//Maklerverwaltungsmenü
	Menu maklerMenu = new Menu("Immobilien-Verwaltung");
	
	
	maklerMenu.addEntry("Neues Haus / Haus aendern", NEWHOUSE);
	maklerMenu.addEntry("Neue Wohnung / Wohnung aendern", NEWFLAT);
	maklerMenu.addEntry("Löschen eines Hauses", DELETEHOUSE);
	maklerMenu.addEntry("Löschen einer Wohnung", DELETEFLAT);
	maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
	
	//Verarbeite Eingabe
	while(true) {
		int response = maklerMenu.show();
		
		switch(response) {
			case NEWHOUSE:
				//HAUS ANLEGEN -------------
				break;
			case NEWFLAT:
				Wohnung w = new Wohnung();	//WOHNUNG ANLEGEN -------------
				w.zuweisen();
				w.save();
				break;
			case DELETEHOUSE:
				//HAUS LÖSCHEN ---------------
				break;
			case DELETEFLAT:
				deleteWohnung(); //ok
				break;
			case BACK:
				return;
		}
	}
}

	public static void deleteWohnung() {
		Wohnung m = new Wohnung();
		
		System.out.println("Geben Sie bitte die ID der Wohnung ein, die gelöscht werden soll.");
		m.delete(""+FormUtil.readInt("ID"));
	}

public static void showVertragMenu() {
	//Menüoptionen
	final int NEW_PERSON = 0;
	final int NEW_CONTRACT = 1;
	final int VIEW_CONTRACTS = 2;
	final int BACK = 3;
	
	//Maklerverwaltungsmenü
	Menu maklerMenu = new Menu("Vertragsmodus");
	
	maklerMenu.addEntry("Person eintragen", NEW_PERSON);
	maklerMenu.addEntry("Vertrag abschließen", NEW_CONTRACT);
	maklerMenu.addEntry("Übersicht über Vertrage", VIEW_CONTRACTS);
	maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
	
	//Verarbeite Eingabe
	while(true) {
		int response = maklerMenu.show();
		
		switch(response) {
			case NEW_PERSON:
				Person p = new Person();	//ok
				p.zuweisen();
				p.save();
				break;
			case NEW_CONTRACT:
				// VERTRÄGE ANLEGEN!!!!!!!------------
				break;
			case VIEW_CONTRACTS:
				Kaufvertrag k = new Kaufvertrag();
				Mietvertrag m = new Mietvertrag();
				k.ausgeben();
				m.ausgeben();
				break;
			case BACK:
				return;
		}
	}
}

}
