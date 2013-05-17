package de.dis2013.host;

import java.io.*;
import java.util.*;

import de.dis2013.host.util.LogEntry;

public class Log {
	static final private Log logger; //needed for Singleton usage
	
	static {
		try {
			logger = new Log();
		}
		catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private Log() {
	}
	
	static public Log getInsatnce() {
		return logger;
	}
	
	
	//real logging functions:
	public void toLog(int lsn, int taid, int pageId, String reDo) {
		LogEntry entry = new LogEntry(lsn, taid, pageId, reDo);
		
		try {
			FileWriter fw = new FileWriter("Log.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//write into the file:
			bw.write(entry.toString());
			bw.newLine();
			bw.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
			
	}
	
	public List<LogEntry> output() {
		List<LogEntry> output = new ArrayList<LogEntry>();
		String line;
		LogEntry entry;
		
		try {
			FileReader fr = new FileReader("Log.txt");
			BufferedReader br = new BufferedReader(fr);
			
			//read the file
			while ((line = br.readLine()) != null ) {
				
				String[] pieces = line.split(" ");
				entry = new LogEntry(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), pieces[3]);
				
				output.add(entry);
			}
			
			br.close();
		} catch (NumberFormatException e) {
			System.out.println("Log besitzt fehlerhafte Dateien Strings statt Zahlen");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return output;
	}
	

}
