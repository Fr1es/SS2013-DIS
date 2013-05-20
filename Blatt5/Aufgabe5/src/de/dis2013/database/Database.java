package de.dis2013.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * This class simulates the database in this system. Pages need a pageID, LSN, Data
 * the PageID is the prim key
 * only pageIDs from 0 to MAX_INDEX_TUPLE are allowed
 * The database is save to the hdd
 * @author Bj�rn Fries, Robert Heinecke
 *
 */
public class Database {
	
	/**
	 * This is the maximum number of database entries. Must be in INT_MAX_SIZE.
	 */
	
	//Singleton
	static final private Database db;
	
	static {
		try {
			db = new Database();
		}
		catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//Singleton
	private Database() {
		System.out.println("de.dis2013.database.Database - constructor");
	}
	
	//Singleton
	static public Database getInstance() {
		return db;
	}
	
	
	
	public void save(int pageId, int lsn, String data) {
		
		try {
			FileWriter fw = new FileWriter("Database\\"+pageId+".txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(""+lsn+" "+data);
			
			System.out.println("de.dis2013.database.Database - saving: PageID: "+pageId+" LSN: "+lsn+" Data: "+data);
			
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//alt mit Array:
//		if (0 <= pageId && pageId <= MAX_INDEX_TUPLE) {
//			
//			System.out.println("de.dis2013.database.Database - saving: PageID: "+pageId+" LSN: "+lsn+" Data: "+data);
//			Tuple t = new Tuple(lsn, data);	
//			database[pageId] = t;
//		}
	}
	
	/**
	 * get LSN from pageID
	 * @param pageId
	 * @return -1 if not found or error!!
	 */
	public int getLsn(int pageId) {
		int returnValue = -1;
		
		try {
			System.out.println("de.dis2013.database.Database - trying to reading: PageID: "+pageId);
			FileReader fr = new FileReader("Database\\"+pageId+".txt");
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			br.close();
			
			String[] pieces = line.split(" ");
			returnValue = Integer.parseInt(pieces[0]);
			
			System.out.println(""+pageId+" found");
		} catch (FileNotFoundException e) {
			
			System.out.println(""+pageId+" NOT found");
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return returnValue;
		
		//altes array:
//		int output;
//		
//		try {
//			output = database[pageId].getLsn();
//		} catch (Exception e) {
//			output = -1;
//		}
//		return output;
		
	}
	
	/**
	 * get LSN from pageID
	 * @param pageId
	 * @return null if not found or error!!
	 */
	public String getData(int pageId) {
		String returnValue = null;
		
		try {
			System.out.println("de.dis2013.database.Database - trying to reading: PageID: "+pageId);
			FileReader fr = new FileReader("Database\\"+pageId+".txt");
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			br.close();
			
			String[] pieces = line.split(" ");
			returnValue = pieces[1];
			
			System.out.println(""+pageId+" found");
		} catch (FileNotFoundException e) {
			
			System.out.println(""+pageId+" NOT found");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return returnValue;		
		
		
		
		
		//altes array:
//		String output;
//		
//		try {
//			output = database[pageId].getData();
//		} catch (Exception e) {
//			output = null;
//		}
//		return output;
		
	}

}
