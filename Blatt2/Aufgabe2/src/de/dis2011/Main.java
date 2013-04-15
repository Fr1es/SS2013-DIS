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
	 * Zeigt das Hauptmenue
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_MAKLER = 0;
		final int IMMOBILIEN = 1;
		final int VERTRAG = 2;
		final int QUIT = 3;
		
		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenue");
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
		//Menueoptionen
		final int NEW_MAKLER = 0;
		final int CHANGE = 1;
		final int DELETE = 2;
		final int BACK = 3;
		
		//Maklerverwaltungsmenue
		Menu maklerMenu = new Menu("Makler-Verwaltung");
		
		if (!maklerMenu.password("admin")) { //Passwortabfrage
			return;
		}
		
		maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
		maklerMenu.addEntry("Maklereintrag ändern", CHANGE);
		maklerMenu.addEntry("Makler löschen", DELETE);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_MAKLER:
					newMakler(); // ANPASSEN!!!! -------------
					break;
				case CHANGE:
					//MAKLEREINTRAG AENDERN!!!!!-------------
					break;
				case DELETE:
					//MARKLEREINTRAG LOESCHEN!!!!!!-------------
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
		
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adresse"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();
		
		System.out.println("Makler mit der ID "+m.getId()+" wurde erzeugt.");
	}


public static void showImmobilienMenu() {
	//Menueoptionen
	final int NEW = 0;
	final int DELETE = 1;
	final int CHANGE = 2;
	final int BACK = 3;
	
	//Maklerverwaltungsmenue
	Menu maklerMenu = new Menu("Immobilien-Verwaltung");
	
	//HIER MUSS SICH EIN MAKLER EINLOGGEN KOENNEN!!!! --------------------------------------------------------
	
	maklerMenu.addEntry("Neue Immobilie", NEW);
	maklerMenu.addEntry("Loeschen einer Immobilie", DELETE);
	maklerMenu.addEntry("Bearbeiten einer Immobilie", CHANGE);
	maklerMenu.addEntry("Zurueck zum Hauptmenue", BACK);
	
	//Verarbeite Eingabe
	while(true) {
		int response = maklerMenu.show();
		
		switch(response) {
			case NEW:
				//IMMOBILIE ANLEGEN -------------
				break;
			case DELETE:
				//IMMOBILIE LOESCHEN ---------------
				break;
			case CHANGE:
				//IMMOBILIE AENDERN ---------------
				break;
			case BACK:
				return;
		}
	}
}

public static void showVertragMenu() {
	//Menueoptionen
	final int NEW_PERSON = 0;
	final int NEW_CONTRACT = 1;
	final int VIEW_CONTRACTS = 2;
	final int BACK = 3;
	
	//Maklerverwaltungsmenue
	Menu maklerMenu = new Menu("Vertragsmodus");
	
	maklerMenu.addEntry("Person eintragen", NEW_PERSON);
	maklerMenu.addEntry("Vertrag abschliessen", NEW_CONTRACT);
	maklerMenu.addEntry("Uebersicht ueber Vertraege", VIEW_CONTRACTS);
	maklerMenu.addEntry("Zurueck zum Hauptmenue", BACK);
	
	//Verarbeite Eingabe
	while(true) {
		int response = maklerMenu.show();
		
		switch(response) {
			case NEW_PERSON:
				// PERSON ANLEGEN !!!- ----------
				break;
			case NEW_CONTRACT:
				// VERTRAEGE ANLEGEN!!!!!!!------------
				break;
			case VIEW_CONTRACTS:
				//VERTRAEGE ANZEIGEN!!!!! ------------------
				break;
			case BACK:
				return;
		}
	}
}

}
