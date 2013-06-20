package de.dis2011.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mietvertrag {
	
	
	
	
	public void ausgeben() {
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		try {			//VERTRAGSNUMMER,DATUM,ORT,MIETBEGINN,DAUER,NEBENKOSTEN,PID,IMMOID
 
				
				//Ausgabe:
				System.out.println("Vertragsnummer: "+vnr);
				//datum?
				System.out.println("Ort: "+ort);
				//Mietbeginn?
				System.out.println("Dauer: "+dauer);
				//Nebenkosten?
				System.out.println("PersonenID: "+pid);
				System.out.println("ImmobilienID: "+immoId);
				System.out.println("------------------------------------");
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
