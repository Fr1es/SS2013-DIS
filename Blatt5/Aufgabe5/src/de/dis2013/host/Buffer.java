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
	 * Add a BufferEntry. Use this function only for writing to the database.
	 * @param e is the BufferEntry you want to add.
	 * @see de.dis2013.host.util.BufferEntry
	 */
	public void addBufferEntry(BufferEntry e) {
		buf.add(e);
		this.performFullCheck();
	}
	
	/**
	 * Add a BufferEntry (made from its base components). Use this function only for writing to the database.
	 * @param lsn
	 * @param taID
	 * @param pageID
	 * @param data
	 * @param commit
	 */
	public void addBufferEntry(int lsn, int taID, int pageID, String data, boolean commit) {
		BufferEntry e = new BufferEntry(lsn, taID, pageID, data, commit);
		this.addBufferEntry(e);
	}
	
	/**
	 * Checks the buffer for its size. Returns true if there are more than {@link #BUFFER_COMMIT_SIZE} entries and false if there are less.
	 * Will also call upon writeAllCommittedTransactionsToDB if necessary (i.e. when it returns true).
	 * @return is true if there are more than {@link #BUFFER_COMMIT_SIZE} entries, else false.
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
	 * @return should always be true. Means, that there are no more committed transactions in the buffer queue.
	 */
	private boolean writeAllCommittedTransactionsToDB() {
		// check from first to last entry if there is a commit in the buffer
		BufferEntry be = this.checkBufferForCommit();
		
		if (be != null) {
			
			// if there is: look for all the BufferEntries with the same TAC
			ArrayList<BufferEntry> temp = this.getElementsByTaID(be.getTaID());
			
			// create DB connection
			Database db = Database.getInstance();
			
			// commit the selected buffer entries to DB
			for (int i=1; i<=temp.size(); i++) {
				db.save(temp.get(i).getPageID(), temp.get(i).getLSN(), temp.get(i).getData());
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
	private BufferEntry checkBufferForCommit() {
		for (int i=1; i<=this.buf.size(); i++) {
			// check for committed transaction in buffer list
			if (this.buf.get(i).getCommit() == true) {
				return this.buf.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Search the buffer for all entries containing a certain transaction ID (taID) and returns an ArrayList with them.
	 * ATTENTION! Also removes all entries containing this taID from the buffer.
	 * @param taID is the transaction ID to look for.
	 * @return will be an ArrayList with BufferEntry elements possessing the specified transaction ID. Will be ordered based upon the time of adding them to the buffer.
	 */
	private ArrayList<BufferEntry> getElementsByTaID(int taID) {
		ArrayList<BufferEntry> temp = new ArrayList<BufferEntry>();
		for (int i=1; i<=buf.size(); i++) {
			if (buf.get(i).getTaID() == taID) {
				// found an element with the same taID: add it to the temporary list
				temp.add(buf.get(i));
				// remove it from the buffer
				buf.remove(i);
				// decrement for not skipping one element
				i--;
			}
		}
		return temp;
	}
}
