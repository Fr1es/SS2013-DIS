package de.dis2013.host.util;

/**
 * This is an entry for the database buffer. Has to contain page identifier, log sequence number, user data and a boolean commit field.
 * @author Robert Heinecke
 */
public class BufferEntry {
	
	/**
	 * Log Sequence Number
	 */
	private int lsn;
	
	/**
	 * Transaction ID
	 */
	private int taID;
	
	/**
	 * Page Identifier
	 */
	private int pageID;
	
	/**
	 * User Data
	 */
	private String data;
	
	/**
	 * Commit Field
	 */
	private boolean commit;
	
	/**
	 * Constructor
	 * @param lsn
	 * @param taID
	 * @param pageID
	 * @param data
	 * @param commit
	 */
	public BufferEntry(int lsn, int taID, int pageID, String data, boolean commit) {
		if (lsn > 0) {
			this.lsn = lsn;
		} else {
			throw new RuntimeException();
		}
		if (taID > 0) {
			this.taID = taID;
		} else {
			throw new RuntimeException();
		}
		if (pageID > 0 || pageID == -99) {
			this.pageID = pageID;
		} else {
			throw new RuntimeException();
		}
		if (data != null) {
			this.data = data;
		} else {
			throw new RuntimeException();
		}
		this.commit = commit;
	}
	
	public int getLSN() {
		return this.lsn;
	}
	
	public int getTaID() {
		return this.taID;
	}
	
	public int getPageID() {
		return this.pageID;
	}
	
	public String getData() {
		return this.data;
	}
	
	public boolean getCommit() {
		return this.commit;
	}
}
