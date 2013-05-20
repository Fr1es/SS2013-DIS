package de.dis2013.host;

import java.util.ArrayList;

import de.dis2013.database.Database;
import de.dis2013.host.util.BufferEntry;

/**
 * This is the buffer handling transactions between the host and the database. Uses @link de.dis2013.host.util.BufferEntry. 
 * Singleton.
 * @see de.dis2013.host.Host, de.dis2013.database.Database, de.dis2013.host.util.BufferEntry
 * @author Robert Heinecke
 *
 */
public class Buffer {
	
	/**
	 * This is the internal list storing all relevant buffer data.
	 */
	ArrayList<BufferEntry> buf = new ArrayList<BufferEntry>();
	
	/**
	 * If the buffer contains more than @link {@link #BUFFER_COMMIT_SIZE} entries, it will write committed transactions to the database.
	 */
	private static final int BUFFER_COMMIT_SIZE = 5;
	
	/**
	 * Set to true for command line debugging.
	 */
	private static final boolean DEBUG = true;
	
	// Singleton
	private static final Buffer buffer;
	static {
		try {
			buffer = new Buffer();
		}
		catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	private Buffer() {}
	public static Buffer getInstance() {
		return buffer;
	}
	
	/**
	 * Use this function to add an entry to the Buffer. This method requires a finished BufferEntry object.
	 * Only @link {@link #addBufferEntry(BufferEntry)} or @link {@link #addBufferEntry(int, int, int, String, boolean)} should be used to write to the database.
	 * @param e is the BufferEntry you want to add.
	 * @see de.dis2013.host.util.BufferEntry
	 */
	public void addBufferEntry(BufferEntry e) {
		if (Buffer.DEBUG) System.out.println("de.dis2013.host.Buffer - saving to buffer: lsn:"+e.getLSN()+" pageID: "+e.getPageID()+" data: "+e.getData()+" commit: "+e.getCommit());
		buf.add(e);
		this.performFullCheck();
	}
	
	/**
	 * Use this function to add an entry to the Buffer. This method creates a BufferEntry object.
	 * Only {@link #addBufferEntry(BufferEntry)} or {@link #addBufferEntry(int, int, int, String, boolean)} should be used to write to the database.
	 * @param lsn
	 * @param taID
	 * @param pageID This is the pageID of the transaction statement. {@link de.dis2013.host.Host} uses -99 as taID for committed transactions. We do not need to check for this, as we use the parameter commit.
	 * @param data
	 * @param commit This parameter should only be true for commit statements. The data and pageID part of such a BufferEntry object will be ignored.
	 */
	public void addBufferEntry(int lsn, int taID, int pageID, String data, boolean commit) {
		BufferEntry e = new BufferEntry(lsn, taID, pageID, data, commit);
		this.addBufferEntry(e);
	}
	
	/**
	 * Checks the buffer for its size. Returns true if there are more than {@link #BUFFER_COMMIT_SIZE} entries and false if there are less.
	 * Will also call upon writeAllCommittedTransactionsToDB if necessary (i.e. when it returns true).
	 * @return The return value is true if there are more than {@link #BUFFER_COMMIT_SIZE} entries, else false.
	 */
	private boolean performFullCheck() {
		if (buf.size() > Buffer.BUFFER_COMMIT_SIZE) {
			
			this.writeAllCommittedTransactionsToDB();
			return true;
			
		} else {
			
			return false;
			
		}
	}
	
	/**
	 * Writes all committed transactions from the buffer to the database. Should only be called upon from @link #addBufferEntry(BufferEntry).
	 * ATTENTION: all entries with commit value set to true are ignored and only used to finish a transaction.
	 * @return The return value should always be true, as this means there are no more committed transactions in the buffer queue.
	 */
	private boolean writeAllCommittedTransactionsToDB() {
		// check from first to last entry if there is a commit in the buffer
		BufferEntry be = this.returnFirstCommittedTransactionFromBuffer();
		
		if (be != null) {
			if (Buffer.DEBUG) System.out.println("de.dis2013.host.Buffer - flushing to DB");
			
			// get all BufferEntry objects with the same taID
			ArrayList<BufferEntry> temp = this.getElementsByTaID(be.getTaID());
			
			// create DB connection
			Database db = Database.getInstance();
			
			// commit the selected buffer entries to DB
			// ignore all pages with the pageID = -99 as this is used for metadata (commit AND bot statements!)
			for (int i=0; i<temp.size(); i++) {
				if (temp.get(i).getPageID() != -99) {
					db.save(temp.get(i).getPageID(), temp.get(i).getLSN(), temp.get(i).getData());
				}
			}
			
			// call this method again to check for other committed transactions in the buffer
			return this.writeAllCommittedTransactionsToDB();
			
		} else {
			// if there isn't: return true
			return true;
		}
	}
	
	/**
	 * Checks the buffer for the first transaction that has been committed and returns its commit entry.
	 * @return will be the last BufferEntry for the first committed transaction found.
	 */
	private BufferEntry returnFirstCommittedTransactionFromBuffer() {
		for (int i=1; i<=this.buf.size(); i++) {
			// check for committed transaction in buffer list
			if (this.buf.get(i-1).getCommit() == true) {
				return this.buf.get(i-1);
			}
		}
		return null;
	}
	
	/**
	 * Search the buffer for all entries containing a certain transaction ID (taID) and returns an ArrayList with them.
	 * ATTENTION: also removes all entries containing this taID from the buffer.
	 * @param taID is the transaction ID to look for.
	 * @return will be an ArrayList with BufferEntry elements possessing the specified transaction ID. Will be ordered based upon the time of adding them to the buffer.
	 */
	private ArrayList<BufferEntry> getElementsByTaID(int taID) {
		
		// create temporary list of buffer entries for a certain transaction
		ArrayList<BufferEntry> temp = new ArrayList<BufferEntry>();
		for (int i=1; i<=buf.size(); i++) {
			
			if (buf.get(i-1).getTaID() == taID) {
				// found an element with the same taID: add it to the temporary list
				temp.add(buf.get(i-1));
				// remove it from the buffer
				buf.remove(i-1);
				// decrement for not skipping one element
				i--;
			}
			
		}
		return temp;
	}
}
