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
	}
	
	public String toString() {
		// TODO: make this return the current state (dependent on current attribute states)
		return null;
	}
	
	public void drillDown(String attributeName) {
		
		// TODO: check whether drill down is possible for this attribute
		if (true) {
			// TODO: enable drill down for an attribute
		} else {
			// no drill down possible for this attribute
			return;
		}
	}
	
	public void rollUp(String attributeName) {
		
		// TODO: check whether roll up is possible for this attribute
		if (true) {
			// TODO: enable roll up for an attribute
		} else {
			// no roll up possible for this attribute
			return;
		}
	}
}
