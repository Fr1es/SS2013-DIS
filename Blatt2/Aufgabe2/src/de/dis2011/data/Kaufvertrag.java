package de.dis2011.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Kaufvertrag {
	
	
	public void ausgeben() {
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		try {
			Statement stm = con.createStatement();
			
			System.out.println("--  Kaufvertraege:  --");
			ResultSet rs = stm.executeQuery("Select VERTRAGSNUMMER,DATUM,ORT,ANZAHLRATEN,RATENZINS,PID,IMMOID FROM kaufvertrag");
			while(rs.next()){
				int vnr = rs.getInt("VERTRAGSNUMMER");
				//datum?
				String ort = rs.getString("ORT");
				int anzR = rs.getInt("ANZAHLRATEN");
				//Ratenzins?
				int pid = rs.getInt("PID");
				int immoId = rs.getInt("IMMOID");
				
				
				//Ausgabe:
				System.out.println("Vertragsnummer: "+vnr);
				//datum?
				System.out.println("Ort: "+ort);
				System.out.println("Anzahl Raten: "+anzR);
				//ratenzins?
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
