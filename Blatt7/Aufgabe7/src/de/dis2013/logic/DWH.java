package de.dis2013.logic;

import java.util.ArrayList;

public class DWH {
	
	/**
	 * private attributes
	 */
	// list of current attribute states
	private ArrayList<String> attributes;
	
	/**
	 * Constructor
	 */
	public DWH() {
		attributes = new ArrayList<String>();
		attributes.add("region");
		attributes.add("time");
		attributes.add("product");
	}
	
	public String toString() {
		// TODO: make this return the current state (dependent on current attribute states)
		return null;
	}
	
	public void drillDown(String attributeName) {
		for (int i=0; i<=attributes.size(); i++) {
			if (attributes.get(i) == attributeName) {
				// TODO: check whether drill down is possible for this attribute
				
				// TODO: do drill down for an attribute
				attributes.set(i, ""); // replace empty string with upper level
				return;
			}
		}
		
		// no drill down possible for this attribute
		return;
	}
	
	public void rollUp(String attributeName) {
		for (int i=0; i<=attributes.size(); i++) {
			if (attributes.get(i) == attributeName) {
				// TODO: check whether roll up is possible for this attribute
				
				// TODO: do roll up for an attribute (i.e. modify current attribute state)
				attributes.set(i, ""); // replace empty string with upper level
				return;
			}
		}
		
		// no roll up possible for this attribute
		return;
	}
}
