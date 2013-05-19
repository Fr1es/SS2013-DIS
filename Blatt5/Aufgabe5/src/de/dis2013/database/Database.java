package de.dis2013.database;

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
	static int MAX_INDEX_TUPLE = 10000;
	
	Tuple[] database = new Tuple[MAX_INDEX_TUPLE];
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
		if (0 <= pageId && pageId <= MAX_INDEX_TUPLE) {
			
			System.out.println("de.dis2013.database.Database - saving: PageID: "+pageId+" LSN: "+lsn+" Data: "+data);
			Tuple t = new Tuple(lsn, data);	
			database[pageId] = t;
		}
	}
	
	/**
	 * get LSN from pageID
	 * @param pageId
	 * @return -1 if not found or error!!
	 */
	public int getLsn(int pageId) {
		int output;
		
		try {
			output = database[pageId].getLsn();
		} catch (Exception e) {
			output = -1;
		}
		return output;
		
	}
	
	/**
	 * get LSN from pageID
	 * @param pageId
	 * @return null if not found or error!!
	 */
	public String getData(int pageId) {
		String output;
		
		try {
			output = database[pageId].getData();
		} catch (Exception e) {
			output = null;
		}
		return output;
		
	}

}
