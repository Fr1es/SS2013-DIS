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
	
	/**
	 * Moves the current hierarchy level for the chosen attribute either up or down.
	 * @param attributeID Has to be either "time" or "place" or "product".
	 * @param upDown Controls drill down (0) or roll up (1).
	 */
	private void iterateAttribute(String attributeID, int upDown) {
		switch (attributeID) {
			case "time":
				for (int i=0; i<time.size(); i++) {
					
					if (time.get(i) == attributes.get(1)) {
						
						if (upDown == 0) {
							if (i == 0) {
								// cant drill down further
							} else {
								attributes.set(1, time.get(i-1));
							}
							
						} else if (upDown == 1) {
							if (i == time.size()-1) {
								// cant roll up further
							} else {
								attributes.set(1, time.get(i+1));
							}
						} else {
							return;
						}
					}
				}
				return;
			case "product":
				for (int i=0; i<product.size(); i++) {
					
					if (product.get(i) == attributes.get(2)) {
						
						if (upDown == 0) {
							if (i == 0) {
								// cant drill down further
							} else {
								attributes.set(2, product.get(i-1));
							}
							
						} else if (upDown == 1) {
							if (i == product.size()-1) {
								// cant roll up further
							} else {
								attributes.set(2, product.get(i+1));
							}
						} else {
							return;
						}
					}
				}
				return;
			case "place":
				for (int i=0; i<place.size(); i++) {
					
					if (place.get(i) == attributes.get(0)) {
						
						if (upDown == 0) {
							if (i == 0) {
								// cant drill down further
							} else {
								attributes.set(0, place.get(i-1));
							}
							
						} else if (upDown == 1) {
							if (i == place.size()-1) {
								// cant roll up further
							} else {
								attributes.set(0, place.get(i+1));
							}
						} else {
							return;
						}
					}
				}
				return;
			default:
				return;
		}
	}
	
	/**
	 * Returns on which hierarchy the specified attribute is right now.
	 * @param attributeID Has to be either "time" or "place" or "product".
	 * @return
	 */
	private String getCurrentIteration(String attributeID) {
		switch (attributeID) {
			case "time":
				return attributes.get(1);
			case "place":
				return attributes.get(0);
			case "product":
				return attributes.get(2);
			default:
				return null;
		}
	}
	
	/**
	 * 
	 */
	public String toString() {
		// TODO: make this return the current state (dependent on current hierarchies)
		return null;
	}
	
	/**
	 * 
	 * @param attributeName
	 */
	public void drillDown(String attributeName) {
		this.iterateAttribute(attributeName, 0);
		return;
	}
	
	/**
	 * 
	 * @param attributeName
	 */
	public void rollUp(String attributeName) {
		this.iterateAttribute(attributeName, 1);
		return;
	}
}
