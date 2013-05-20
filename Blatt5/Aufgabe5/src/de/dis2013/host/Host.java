package de.dis2013.host;

import java.util.ArrayList;
import java.util.List;

import de.dis2013.database.Database;
import de.dis2013.host.util.LogEntry;
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
		System.out.println("de.dis2013.host.Host - START constructor");
		crashRecovery();
		System.out.println("de.dis2013.host.Host - END constructor");
	}
	
	static public Host getInstance() {
		return HOST;
	}
	
	//error host functions:
	private void crashRecovery(){
		//TODO happened a crash? find last nrLSN and nrTAID and reset to them. redo TAs!
		
		lsn.set(0);//for now
		taid.set(0);//for now
		
		System.out.println("de.dis2013.host.Host - START crashRecovery");
		List<LogEntry> log = Log.getInstance().output();
		//List for Winners:
		List<Integer> winners = new ArrayList<Integer>();
		
		//write down the taid for every taid with a commit statement in the log
		for (int i = 0; i<log.size(); i++) {
			if (log.get(i).getRedo().equals("commit")) { //if it is a commit
				if (!winners.contains( log.get(i).getTaid() ) ) { //if it is not already in the winners list (should not happen but for debugging..)
					winners.add( log.get(i).getTaid() );
				}
			}
		}
		
		//redo phase
		LogEntry entry;
		for (int i = 0; i<log.size(); i++) {
			entry = log.get(i);
			
			if ( entry.getPageId() != -99 ) {//the entry is no Metadata...
				if ( winners.contains( entry.getTaid()) ) { //if the entry in the log is a winner taid:
					if ( Database.getInstance().getLsn(entry.getPageId()) < entry.getLsn() ) { //if the lsn in the log is newer than the lsn in the DB (incl -1 for not found)
						Database.getInstance().save(entry.getPageId(), entry.getLsn(), entry.getRedo());
						System.out.println("REDONE: pageID:"+entry.getPageId()+" LSN: "+entry.getLsn()+" Data: "+entry.getRedo());
					}
				}
			}
		}
		System.out.println("de.dis2013.host.Host - END crashRecovery");
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
