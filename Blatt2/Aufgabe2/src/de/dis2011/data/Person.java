package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2011.FormUtil;

public class Person {
	private int pid;
	private String vorname;
	private String nachname;
	private String adresse;
	
	public void zuweisen() {
		System.out.println("Geben Sie bitte die Daten der Person ein, die gespeichert werden sollen." +
				"\nJede PersonenID wird nur einmal vergeben");
		pid = FormUtil.readInt("PersonenID");
		vorname = FormUtil.readString("Vorname");
		nachname = FormUtil.readString("Nachname");
		adresse = FormUtil.readString("Adresse");

	}
	
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		try {
			Statement stm = con.createStatement();
			
			ResultSet rs = stm.executeQuery("Select * FROM person WHERE pid = '"+ pid +"'");

			
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (!rs.next()) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO wohnung(PID,VORNAME,NACHNAME,ADRESSE) VALUES (?,?,?,?)";
				
				PreparedStatement pstmt = con.prepareStatement(insertSQL);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setInt(	1	,	pid	);
				pstmt.setString(	2	,	vorname	);
				pstmt.setString(	3	,	nachname	);
				pstmt.setString(	4	,	adresse );
				pstmt.executeUpdate();

				pstmt.close();
				System.out.println("Die Person mit der ID "+pid+" wurde erstellt.");
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE person SET VORNAME=?,NACHNAME=?,ADRESSE=? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(	1	, vorname	);
				pstmt.setString(	2	, nachname	);
				pstmt.setString(	3	, adresse	);
				pstmt.setInt(	4	, pid	);


				pstmt.executeUpdate();

				pstmt.close();
				System.out.println("Die Eintraege der Person mit der ID "+pid+" wurden aktualisiert.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}


//SELECT PID,VORNAME,NACHNAME,ADRESSE FROM "VSISP17"."PERSON";

