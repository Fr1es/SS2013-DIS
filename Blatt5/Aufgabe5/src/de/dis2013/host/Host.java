package de.dis2013.host;

import de.dis2013.host.util.NrLSN;
import de.dis2013.host.util.NrTAID;


public class Host {
	NrLSN lsn = new NrLSN();
	NrTAID taid = new NrTAID();
	static final private Host HOST; //needed for Singleton usage
	
	static {
		try {
			HOST = new Host();
		}
		catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Host() {
		System.out.println("de.dis2013.host.Host - constructor");
		crashRecovery();
	}
	
	static public Host getInstance() {
		return HOST;
	}
	
	//error host functions:
	private void crashRecovery(){
		//TODO happened a crash? find last nrLSN and nrTAID and reset to them. redo TAs!
		
		lsn.set(0);//for now
		taid.set(0);//for now
	}
	
	
	//normal host functions:
	public int beginTransaction() {
		//logging BOT Entry?!
		taid.increment(); //increment the return value
		lsn.increment();
		
		//logging:
		Log.getInstance().toLog(lsn.get(), taid.get(), -99, "bot");
		
		return taid.get();
	}
	
	/**
	 * Commits transaction from buffer to database.
	 * @param taid is the ID of the transaction that will be committed.
	 */
	public void commit(int taid) {
		lsn.increment();
		
		
		//logging:
		Log.getInstance().toLog(lsn.get(), taid, -99, "commit");
		
		//to DB:
		Buffer.getInstance().addBufferEntry(lsn.get(), taid, -99, "", true);
	}
	
	/**
	 * Writes transaction into the buffer.
	 * @param taid
	 * @param pageid
	 * @param data
	 */
	public void write(int taid, int pageid, String data) {
		lsn.increment();
		
		//logging:
		Log.getInstance().toLog(lsn.get(), taid, pageid, data);
		
		//to buffer:
		Buffer.getInstance().addBufferEntry(lsn.get(), taid, pageid, data, false);
	}

}
