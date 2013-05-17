package de.dis2013.host.util;

/**
 * Entry in a log
 * a logentry can never be changed!
 * @author Björn Fries
 *
 */
public class LogEntry {
	private final int LSN;
	private final int TAID;
	private final int PAGEID;
	private final String REDO;
	
	public LogEntry(int lsn, int taid, int pageId, String reDo) {
		this.LSN = lsn;
		this.TAID = taid;
		this.PAGEID = pageId;
		this.REDO = reDo;
	}
	
	
	public int getLsn() {
		return LSN;
	}	
	public int getTaid() {
		return TAID;
	}
	public int getPageId() {
		return PAGEID;
	}
	public String getRedo() {
		return REDO;
	}
	
	//toString for easy Entry in log.txt
	public String toString() {
		return(""+LSN+" "+TAID+" "+PAGEID+" "+REDO);
	}

}
