package de.dis2013.data;

import de.dis2013.util.Helper;

/**
 * Haus-Bean
 */
public class Haus{
	private int id;
	private String ort;
	private int plz;
	private String strasse;
	private int hausnr;
	private int flaeche;
	private int stockwerke;
	private int kaufpreis;
	private char garten;
	private Makler verwalter;
	
	public Haus() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public int getHausnr() {
		return hausnr;
	}
	public void setHausnr(int hausnr) {
		this.hausnr = hausnr;
	}

	public int getFlaeche() {
		return flaeche;
	}
	public void setFlaeche(int flaeche) {
		this.flaeche = flaeche;
	}
	
	public int getStockwerke() {
		return stockwerke;
	}
	public void setStockwerke(int stockwerke) {
		this.stockwerke = stockwerke;
	}
	public int getKaufpreis() {
		return kaufpreis;
	}
	public void setKaufpreis(int kaufpreis) {
		this.kaufpreis = kaufpreis;
	}
	public char isGarten() {
		return garten;
	}
	public void setGarten(char garten) {
		this.garten = garten;
	}
	
	public Makler getVerwalter() {
		return verwalter;
	}
	public void setVerwalter(Makler verwalter) {
		this.verwalter = verwalter;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		
		result = prime * result + getStockwerke();
		result = prime * result + getKaufpreis();
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || !(obj instanceof Haus))
			return false;
	
		Haus other = (Haus)obj;
	
		if(other.getId() != getId() ||
				other.getPlz() != getPlz() ||
				other.getFlaeche() != getFlaeche() ||
				!Helper.compareObjects(this.getOrt(), other.getOrt()) ||
				!Helper.compareObjects(this.getStrasse(), other.getStrasse()) ||
				!Helper.compareObjects(this.getHausnr(), other.getHausnr()) ||
				getStockwerke() != other.getStockwerke() ||
				getKaufpreis() != other.getKaufpreis() ||
				isGarten() != other.isGarten())
		{
			return false;
		}
		
		return true;
	}
}
