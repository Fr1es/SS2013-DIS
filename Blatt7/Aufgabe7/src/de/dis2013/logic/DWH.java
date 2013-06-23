package de.dis2013.logic;

import java.util.ArrayList;

public class DWH {
	
	/**
	 * private attributes
	 */
	// list of current attribute states
	private ArrayList<String> attributes;
	
	private ArrayList<String> place;
	private ArrayList<String> time;
	private ArrayList<String> product;
	
	/**
	 * Constructor
	 */
	public DWH() {
		// list of possible place hierarchies
		place = new ArrayList<String>();
		place.add("name");
		place.add("town");
		place.add("region");
		place.add("country");
		
		// list of possible time hierarchies
		time = new ArrayList<String>();
		time.add("dayID");
		time.add("month");
		time.add("quarter");
		time.add("year");
		
		// list of possible product hierarchies
		product = new ArrayList<String>();
		product.add("name");
		product.add("productGroup");
		product.add("productFamily");
		product.add("productCategory");
		
		// list of current hierarchies
		attributes = new ArrayList<String>();
		attributes.add("town"); // place - i.e. name, town, region, country
		attributes.add("quarter"); // time - i.e. dayID, month, quarter, year
		attributes.add("name"); // product - i.e. name, productGroup, productFamily, productCategory
	}
	
	public String toString() {
		// TODO: make this return the current state (dependent on current hierarchies)
		return null;
	}
	
	/**
	 * 
	 * @param attributeName
	 */
	public void drillDown(String attributeName) {
	
	
		// TODO: check whether drill down is possible for this attribute
		
		// TODO: do drill down for an attribute
		
		return;
	}
	
	public void rollUp(String attributeName) {

		// TODO: check whether roll up is possible for this attribute
		
		// TODO: do roll up for an attribute (i.e. modify current attribute state)

		return;
	}
}
