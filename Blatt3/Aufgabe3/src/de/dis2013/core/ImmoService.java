package de.dis2013.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.dis2013.data.Haus;
//import de.dis2013.data.Immobilie;
import de.dis2013.data.Kaufvertrag;
import de.dis2013.data.Makler;
import de.dis2013.data.Mietvertrag;
import de.dis2013.data.Person;
import de.dis2013.data.Wohnung;

/**
 * Klasse zur Verwaltung aller Datenbank-Entitäten.
 * 
 * TODO: Aktuell werden alle Daten im Speicher gehalten. Ziel der Übung
 * ist es, schrittweise die Datenverwaltung in die Datenbank auszulagern.
 * Wenn die Arbeit erledigt ist, werden alle Sets dieser Klasse überflüssig.
 */
public class ImmoService {
	//Datensätze im Speicher
//	private Set<Makler> makler = new HashSet<Makler>();
//	private Set<Person> personen = new HashSet<Person>();
//	private Set<Haus> haeuser = new HashSet<Haus>();
//	private Set<Wohnung> wohnungen = new HashSet<Wohnung>();
//	private Set<Mietvertrag> mietvertraege = new HashSet<Mietvertrag>();
//	private Set<Kaufvertrag> kaufvertraege = new HashSet<Kaufvertrag>();
	
	//Hibernate Session
	private SessionFactory sessionFactory;
	private Session session;
	
	public ImmoService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * Finde einen Makler mit gegebener Id
	 * @param id Die ID des Maklers
	 * @return Makler mit der ID oder null
	 */
	public Makler getMaklerById(int id) {
		/*
		Iterator<Makler> it = makler.iterator();
		
		while(it.hasNext()) {
			Makler m = it.next();
			
			if(m.getId() == id)
				return m;
		}
		
		return null;
		*/
		
		session = sessionFactory.openSession();
		session.beginTransaction();		
		
		Makler ausgabe = (Makler) session.get(Makler.class, id);
		
		session.getTransaction().commit();
		session.close();
		
		return ausgabe;
	}
	
	/**
	 * Finde einen Makler mit gegebenem Login
	 * @param login Der Login des Maklers
	 * @return Makler mit der ID oder null
	 */
	public Makler getMaklerByLogin(String login) {
//		Iterator<Makler> it = makler.iterator();
//		
//		while(it.hasNext()) {
//			Makler m = it.next();
//			
//			if(m.getLogin().equals(login))
//				return m;
//		}
//		
//		return null;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session.createQuery("from Makler as makler where makler.login = ?").setString(0, login).list();
		
		session.getTransaction().commit();
		session.close();
		
		if (ausgabe.size() == 0)
		{
			return null;
		}
			
		return (Makler) ausgabe.get(0);
		
	}
	
	/**
	 * Gibt alle Makler zurück
	 */
	public Set<Makler> getAllMakler() {
//		return makler;

		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session.createQuery("from Makler as makler").list();
		
		session.getTransaction().commit();
		session.close();		
		
		Set<Makler> ausgabeSet = new HashSet<Makler>(ausgabe);
		return ausgabeSet;
		
	}
	
	/**
	 * Finde eine Person mit gegebener Id
	 * @param id Die ID der Person
	 * @return Person mit der ID oder null
	 */
	public Person getPersonById(int id) {
		/*
		Iterator<Person> it = personen.iterator();
		
		while(it.hasNext()) {
			Person p = it.next();
			
			if(p.getId() == id)
				return p;
		}
		
		return null;
		*/
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		Person ausgabe = (Person) session.get(Person.class, id);
		
		session.getTransaction().commit();
		session.close();
		
		return ausgabe;
	}
	
	/**
	 * Fügt einen Makler hinzu
	 * @param m Der Makler
	 */
	public void addMakler(Makler m) {
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		
		//das alte Set:
//		makler.add(m);
	}
	
	/**
	 * Löscht einen Makler
	 * @param m Der Makler
	 */
	public void deleteMakler(Makler m) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(m);
		session.getTransaction().commit();
		
		
		//das alte Set:
//		makler.remove(m);
	}
	
	/**
	 * Fügt eine Person hinzu
	 * @param p Die Person
	 */
	public void addPerson(Person p) {
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
		
		
		//das alte set:
//		personen.add(p);
	}
	
	/**
	 * Gibt alle Personen zurück
	 */
	public Set<Person> getAllPersons() {
//		return personen;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session.createQuery("from Person as person").list();
		
		session.getTransaction().commit();
		session.close();		
		
		Set<Person> ausgabeSet = new HashSet<Person>(ausgabe);
		return ausgabeSet;
		
	}
	
	/**
	 * Löscht eine Person
	 * @param p Die Person
	 */
	public void deletePerson(Person p) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
		
		//das alte Set:
//		personen.remove(p);
	}
	
	/**
	 * Fügt ein Haus hinzu
	 * @param h Das Haus
	 */
	public void addHaus(Haus h) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(h);
		session.getTransaction().commit();

		
		
		//das alte Set:
//		haeuser.add(h);
	}
	
	/**
	 * Gibt alle Häuser eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Häuser, die vom Makler verwaltet werden
	 */
	public Set<Haus> getAllHaeuserForMakler(Makler m) {
//		Set<Haus> ret = new HashSet<Haus>();
//		Iterator<Haus> it = haeuser.iterator();
//		
//		while(it.hasNext()) {
//			Haus h = it.next();
//			
//			if(h.getVerwalter().equals(m))
//				ret.add(h);
//		}
//		
//		return ret;
		
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session.createQuery("from Haus as haus where verwalter = ?").setEntity(0, m).list();
		
		session.getTransaction().commit();
		session.close();		
		
		Set<Haus> ausgabeSet = new HashSet<Haus>(ausgabe);
		return ausgabeSet;
		
	}
	
	/**
	 * Findet ein Haus mit gegebener ID
	 * @param m Der Makler
	 * @return Das Haus oder null, falls nicht gefunden
	 */
	public Haus getHausById(int id) {
//		Iterator<Haus> it = haeuser.iterator();
//		
//		while(it.hasNext()) {
//			Haus h = it.next();
//			
//			if(h.getId() == id)
//				return h;
//		}
//		
//		return null;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		Haus ausgabe = (Haus) session.get(Haus.class, id);
		
		session.getTransaction().commit();
		session.close();
		
		return ausgabe;
	}
	
	/**
	 * Löscht ein Haus
	 * @param p Das Haus
	 */
	public void deleteHouse(Haus h) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(h);
		session.getTransaction().commit();
		
		
		//das alte Set:
//		haeuser.remove(h);
	}
	
	/**
	 * Fügt eine Wohnung hinzu
	 * @param w die Wohnung
	 */
	public void addWohnung(Wohnung w) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();
		
		
		//das alte Set:
//		wohnungen.add(w);
	}
	
	/**
	 * Gibt alle Wohnungen eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Wohnungen, die vom Makler verwaltet werden
	 */
	public Set<Wohnung> getAllWohnungenForMakler(Makler m) {
//		Set<Wohnung> ret = new HashSet<Wohnung>();
//		Iterator<Wohnung> it = wohnungen.iterator();
//		
//		while(it.hasNext()) {
//			Wohnung w = it.next();
//			
//			if(w.getVerwalter().equals(m))
//				ret.add(w);
//		}
//		
//		return ret;
		
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session.createQuery("from Wohnung as wohnung where verwalter = ?").setEntity(0, m).list();
		
		session.getTransaction().commit();
		session.close();		
		
		Set<Wohnung> ausgabeSet = new HashSet<Wohnung>(ausgabe);
		return ausgabeSet;
	}
	
	/**
	 * Findet eine Wohnung mit gegebener ID
	 * @param id Die ID
	 * @return Die Wohnung oder null, falls nicht gefunden
	 */
	public Wohnung getWohnungById(int id) {
//		Iterator<Wohnung> it = wohnungen.iterator();
//		
//		while(it.hasNext()) {
//			Wohnung w = it.next();
//			
//			if(w.getId() == id)
//				return w;
//		}
//		
//		return null;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		Wohnung ausgabe = (Wohnung) session.get(Wohnung.class, id);
		session.getTransaction().commit();
		session.close();
		
		return ausgabe;
	}
	
	/**
	 * Löscht eine Wohnung
	 * @param p Die Wohnung
	 */
	public void deleteWohnung(Wohnung w) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(w);
		session.getTransaction().commit();
		
		
		//das alte set:
//		wohnungen.remove(w);
	}
	
	
	/**
	 * Fügt einen Mietvertrag hinzu
	 * @param w Der Mietvertrag
	 */
	public void addMietvertrag(Mietvertrag m) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		
		//das alte Set:
//		mietvertraege.add(m);
	}
	
	/**
	 * Fügt einen Kaufvertrag hinzu
	 * @param w Der Kaufvertrag
	 */
	public void addKaufvertrag(Kaufvertrag k) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		//session.save(k.getHaus());
		session.save(k);
		session.getTransaction().commit();
		
		
		//das alte set:
//		kaufvertraege.add(k);
	}
	
	/**
	 * Gibt alle Mietverträge zu Wohnungen eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Mietverträge, die zu Wohnungen gehören, die vom Makler verwaltet werden
	 */
	public Set<Mietvertrag> getAllMietvertraegeForMakler(Makler m) {
//		Set<Mietvertrag> ret = new HashSet<Mietvertrag>();
//		Iterator<Mietvertrag> it = mietvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Mietvertrag v = it.next();
//			
//			if(v.getWohnung().getVerwalter().equals(m))
//				ret.add(v);
//		}
//		
//		return ret;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session
				.createQuery("select wohnung.mietvertraege from Wohnung as wohnung where wohnung.verwalter = ?")
				.setEntity(0, m)
				.list();
		
		session.getTransaction().commit();
//		session.close();		
		
		Set<Mietvertrag> ausgabeSet = new HashSet<Mietvertrag>(ausgabe);
		return ausgabeSet;
	}
	
	/**
	 * Gibt alle Kaufverträge zu Wohnungen eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Kaufverträge, die zu Häusern gehören, die vom Makler verwaltet werden
	 */
	public Set<Kaufvertrag> getAllKaufvertraegeForMakler(Makler m) {
//		Set<Kaufvertrag> ret = new HashSet<Kaufvertrag>();
//		Iterator<Kaufvertrag> it = kaufvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Kaufvertrag k = it.next();
//			
//			if(k.getHaus().getVerwalter().equals(m))
//				ret.add(k);
//		}
//		
//		return ret;
		
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session
				.createQuery("select haus.kaufvertraege from Haus as haus where haus.verwalter = ?")
				.setEntity(0, m)
				.list();
		
		session.getTransaction().commit();
//		session.close();		
		
		Set<Kaufvertrag> ausgabeSet = new HashSet<Kaufvertrag>(ausgabe);
		return ausgabeSet;
	}
	
	/**
	 * Findet einen Mietvertrag mit gegebener ID
	 * @param id Die ID
	 * @return Der Mietvertrag oder null, falls nicht gefunden
	 */
	public Mietvertrag getMietvertragById(int id) {
//		Iterator<Mietvertrag> it = mietvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Mietvertrag m = it.next();
//			
//			if(m.getId() == id)
//				return m;
//		}
//		
//		return null;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		Mietvertrag ausgabe = (Mietvertrag) session.get(Mietvertrag.class, id);
		
		session.getTransaction().commit();
		session.close();
		return ausgabe;
	}
	
	/**
	 * Findet alle Mietverträge, die Wohnungen eines gegebenen Verwalters betreffen
	 * @param id Der Verwalter
	 * @return Set aus Mietverträgen
	 */
	public Set<Mietvertrag> getMietvertragByVerwalter(Makler m) { //das hier wird gar nicht genutzt... wtf?!
//		Set<Mietvertrag> ret = new HashSet<Mietvertrag>();
//		Iterator<Mietvertrag> it = mietvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Mietvertrag mv = it.next();
//			
//			if(mv.getWohnung().getVerwalter().getId() == m.getId())
//				ret.add(mv);
//		}
//		
//		return ret;
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session
				.createQuery("select wohnung.mietvertraege from Wohnung as wohnung where wohnung.verwalter = ?")
				.setEntity(0, m)
				.list();
		
		session.getTransaction().commit();
//		session.close();		
		
		Set<Mietvertrag> ausgabeSet = new HashSet<Mietvertrag>(ausgabe);
		return ausgabeSet;
		
	}
	
	/**
	 * Findet alle Kaufverträge, die Häuser eines gegebenen Verwalters betreffen
	 * @param id Der Verwalter
	 * @return Set aus Kaufverträgen
	 */
	public Set<Kaufvertrag> getKaufvertragByVerwalter(Makler m) { //wann wird das hier verwendet?!
//		Set<Kaufvertrag> ret = new HashSet<Kaufvertrag>();
//		Iterator<Kaufvertrag> it = kaufvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Kaufvertrag k = it.next();
//			
//			if(k.getHaus().getVerwalter().getId() == m.getId())
//				ret.add(k);
//		}
//		
//		return ret;
		
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		List ausgabe = session
				.createQuery("select haus.kaufvertraege from Haus as haus where haus.verwalter = ?")
				.setEntity(0, m)
				.list();
		
		session.getTransaction().commit();
//		session.close();		
		
		Set<Kaufvertrag> ausgabeSet = new HashSet<Kaufvertrag>(ausgabe);
		return ausgabeSet;
	}
	
	/**
	 * Findet einen Kaufvertrag mit gegebener ID
	 * @param id Die ID
	 * @return Der Kaufvertrag oder null, falls nicht gefunden
	 */
	public Kaufvertrag getKaufvertragById(int id) {
//		Iterator<Kaufvertrag> it = kaufvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Kaufvertrag k = it.next();
//			
//			if(k.getId() == id)
//				return k;
//		}
//		
//		return null;
		

		session.beginTransaction();
		
		Kaufvertrag ausgabe = (Kaufvertrag) session.get(Kaufvertrag.class, id);
		
		session.getTransaction().commit();
		session.close();
		return ausgabe;
	}
	
	/**
	 * Löscht einen Mietvertrag
	 * @param m Der Mietvertrag
	 */
	public void deleteMietvertrag(Mietvertrag m) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(m);
		session.getTransaction().commit();
		
		//das alte set:
//		wohnungen.remove(m);
	}
	
	/**
	 * Update Makler
	 * NEU
	 */
	public void updateMakler(Makler m){
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(m);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Update Person
	 * NEU
	 */
	public void updatePerson(Person p){
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Update Haus
	 * NEU
	 */
	public void updateHaus(Haus h){
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(h);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Update Wohnung
	 * NEU
	 */
	public void updateWohnung(Wohnung w){
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(w);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Fügt einige Testdaten hinzu
	 */
	public void addTestData() {
		//Hibernate Session erzeugen
		session = sessionFactory.openSession();
		
		//-----------------------MAKLER
		Makler m = new Makler();
		m.setName("Max Mustermann");
		m.setAdresse("Am Informatikum 9");
		m.setLogin("max");
		m.setPasswort("max");
		
		//TODO: Dieser Makler wird im Speicher und der DB gehalten
		this.addMakler(m);

		
		//-----------------------PERSONEN
		Person p1 = new Person();
		p1.setAdresse("Informatikum");
		p1.setNachname("Mustermann");
		p1.setVorname("Erika");
		
		
		Person p2 = new Person();
		p2.setAdresse("Reeperbahn 9");
		p2.setNachname("Albers");
		p2.setVorname("Hans");
		
		//TODO: Diese Personen werden im Speicher und der DB gehalten
		this.addPerson(p1);
		this.addPerson(p2);

		
		//-----------------------HAUS
		//Hibernate Session erzeugen
		Haus h = new Haus();
		h.setOrt("Hamburg");
		h.setPlz(22527);
		h.setStrasse("Vogt-Kölln-Straße");
		h.setHausnummer("2a");
		h.setFlaeche(384);
		h.setStockwerke(5);
		h.setKaufpreis(10000000);
		h.setGarten(true);
		h.setVerwalter(m);
		//TODO: Dieses Haus wird im Speicher und der DB gehalten
		
		
		/*
		//-----------------------AUSGABE IMMO
		//Hibernate Session erzeugen
		session.beginTransaction();
		Makler m2 = (Makler)session.get(Makler.class, m.getId());
		Set<Immobilie> immos = m2.getImmobilien();
		Iterator<Immobilie> it = immos.iterator();
		
		while(it.hasNext()) {
			Immobilie i = it.next();
			System.out.println("Immo: "+i.getOrt());
		}
		
		session.getTransaction().commit();
		
		*/
		
		//-----------------------WOHNUNG		
		Wohnung w = new Wohnung();
		w.setOrt("Hamburg");
		w.setPlz(22527);
		w.setStrasse("Vogt-Kölln-Straße");
		w.setHausnummer("3");
		w.setFlaeche(120);
		w.setStockwerk(4);
		w.setMietpreis(790);
		w.setEbk(true);
		w.setBalkon(false);
		w.setVerwalter(m);
		this.addWohnung(w);

		w = new Wohnung();
		w.setOrt("Berlin");
		w.setPlz(22527);
		w.setStrasse("Alexanderplatz");
		w.setHausnummer("3");
		w.setFlaeche(120);
		w.setStockwerk(4);
		w.setMietpreis(790);
		w.setEbk(true);
		w.setBalkon(false);
		w.setVerwalter(m);
		this.addWohnung(w);
		
		//-----------------------KAUFVERTRAG
		Kaufvertrag kv = new Kaufvertrag();
		kv.setHaus(h);
		kv.setVertragspartner(p1);
		kv.setVertragsnummer(9234);
		kv.setDatum(new Date(System.currentTimeMillis()));
		kv.setOrt("Hamburg");
		kv.setAnzahlRaten(5);
		kv.setRatenzins(4);
		this.addHaus(h);
		this.addKaufvertrag(kv);
		
		//-----------------------MIETVERTRAG
		Mietvertrag mv = new Mietvertrag();
		mv.setWohnung(w);
		mv.setVertragspartner(p2);
		mv.setVertragsnummer(23112);
		mv.setDatum(new Date(System.currentTimeMillis()-1000000000));
		mv.setOrt("Berlin");
		mv.setMietbeginn(new Date(System.currentTimeMillis()));
		mv.setNebenkosten(65);
		mv.setDauer(36);
		this.addMietvertrag(mv);

		
		session.close();//////2
	}
}
