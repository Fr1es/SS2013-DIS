package de.dis2011;

import de.dis2011.data.Makler;

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
					newMakler(); // ANPASSEN!!!! -------------
					break;
				case DELETE:
					//MARKLEREINTRAG LÖSCHEN!!!!!!-------------
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
		
		System.out.println("Geben Sie die Daten eines Maklers ein, den Sie erstellen wollen." +
				"\nJeder Login wird nur einmal vergeben");
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adresse"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();
		
		System.out.println("Makler mit dem Login "+m.getLogin()+" wurde gespeichert.");
	}


public static void showImmobilienMenu() {
	//Menüoptionen
	final int NEWHOUSE = 0;
	final int NEWFLAT = 1;
	final int DELETEHOUSE = 2;
	final int DELETEFLAT = 3;
	final int CHANGE = 4;
	final int BACK = 5;
	
	//Maklerverwaltungsmenü
	Menu maklerMenu = new Menu("Immobilien-Verwaltung");
	
	//HIER MUSS SICH EIN MAKLER EINLOGGEN KÖNNEN!!!! --------------------------------------------------------
	
	maklerMenu.addEntry("Neues Haus", NEWHOUSE);
	maklerMenu.addEntry("Neue Wohnung", NEWFLAT);
	maklerMenu.addEntry("Löschen eines Hauses", DELETEHOUSE);
	maklerMenu.addEntry("Löschen einer Wohnung", DELETEFLAT);
	maklerMenu.addEntry("Bearbeiten einer Immobilie", CHANGE);
	maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
	
	//Verarbeite Eingabe
	while(true) {
		int response = maklerMenu.show();
		
		switch(response) {
			case NEWHOUSE:
				//HAUS ANLEGEN -------------
				break;
			case NEWFLAT:
				//WOHNUNG ANLEGEN -------------
				break;
			case DELETEHOUSE:
				//HAUS LÖSCHEN ---------------
				break;
			case DELETEFLAT:
				//WOHNUNG LÖSCHEN ---------------
				break;
			case CHANGE:
				//IMMOBILIE ÄNDERN ---------------
				break;
			case BACK:
				return;
		}
	}
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
				// PERSON ANLEGEN !!!- ----------
				break;
			case NEW_CONTRACT:
				// VERTRÄGE ANLEGEN!!!!!!!------------
				break;
			case VIEW_CONTRACTS:
				//VERTRÄGE ANZEIGEN!!!!! ------------------
				break;
			case BACK:
				return;
		}
	}
}

}
