package de.dis2013.database;

/**
 * a tuple is part of the Database. the lsn and data are the parts of the page structure
 * @author Björn Fries
 *
 */
public class Tuple {
	private int lsn;
	private String data;
	
	public Tuple(int lsn, String data) {
		this.lsn = lsn;
		this.data = data;
	}
	
	//getter
	public int getLsn(){
		return lsn;
	}
	public String getData(){
		return data;
	}
	
	//setter
	public void setLsn(int lsn){
		this.lsn = lsn;
	}
	public void setData(String data){
		this.data = data;
	}

}
