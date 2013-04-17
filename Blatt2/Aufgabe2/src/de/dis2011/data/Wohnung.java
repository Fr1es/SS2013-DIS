package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2011.FormUtil;
import de.dis2011.data.DB2ConnectionManager;

public class Wohnung {
	private int id;
	private String mLogin;
	private String ort;
	private String plz;
	private String strasse;
	private int hausnummer;
	private double flaeche;
	private int stockwerk;
	private double mietpreis;
	private int zimmer;
	private char balkon;
	private char ebk;
	
	public void zuweisen() {
		System.out.println("Geben Sie bitte die Daten der Wohnung ein, die gespeichert werden sollen." +
				"\nJede ID wird nur einmal vergeben");
		id = FormUtil.readInt("ID");
		mLogin = FormUtil.readString("Wird Verwaltet von (Maklerlogin)");
		ort = FormUtil.readString("Ort");
		plz = FormUtil.readString("PLZ");
		strasse = FormUtil.readString("Strasse");
		hausnummer = FormUtil.readInt("Hausnummer");
		flaeche = FormUtil.readDouble("Flaeche");
		stockwerk = FormUtil.readInt("Stockwerk");
		mietpreis = FormUtil.readPreis("Mietpreis");
		zimmer = FormUtil.readInt("Zimmer");
		balkon = FormUtil.readChar("Balkon");
		ebk = FormUtil.readChar("EBK");
	}
	
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		try {
			Statement stm = con.createStatement();
			
			ResultSet rs = stm.executeQuery("Select * FROM makler WHERE login = '"+ id +"'");

			
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (!rs.next()) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO wohnung(ID,MLOGIN,ORT,PLZ,STRASSE,HAUSNR,FLAECHE,STOCKWERK,MIETPREIS,ZIMMER,BALKON,EBK) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println("Hier");
				
				PreparedStatement pstmt = con.prepareStatement(insertSQL);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setInt(	1	,	id	);
				pstmt.setString(	2	,	mLogin	);
				pstmt.setString(	3	,	ort	);
				pstmt.setString(	4	,	plz	);
				pstmt.setString(	5	,	strasse	);
				pstmt.setInt(	6	,	hausnummer	);
				pstmt.setDouble(	7	,	flaeche	);
				pstmt.setInt(	8	,	stockwerk	);
				pstmt.setDouble(	9	,	mietpreis	);
				pstmt.setInt(	10	,	zimmer	);
				pstmt.setString(	11	,	""+balkon	);
				pstmt.setString(	12	,	""+ebk	);
				pstmt.executeUpdate();

				pstmt.close();
				System.out.println("Die Wohnung mit der ID "+id+" wurde erstellt.");
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE makler SET mLogin=?,ort=?,plz=?,strasse=?,hausnummer=?,flaeche=?,stockwerk=?,mietpreis=?,zimmer=?,balkon=?,ebk=? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(	1	, ""+	mLogin	);
				pstmt.setString(	2	, ""+	ort	);
				pstmt.setString(	3	, ""+	plz	);
				pstmt.setString(	4	, ""+	strasse	);
				pstmt.setString(	5	, ""+	hausnummer	);
				pstmt.setString(	6	, ""+	flaeche	);
				pstmt.setString(	7	, ""+	stockwerk	);
				pstmt.setString(	8	, ""+	mietpreis	);
				pstmt.setString(	9	, ""+	zimmer	);
				pstmt.setString(	10	, ""+	balkon	);
				pstmt.setString(	11	, ""+	ebk	);
				pstmt.setString(	12	, ""+	id	);


				pstmt.executeUpdate();

				pstmt.close();
				System.out.println("Die Eintraege der Wohnung mit der ID "+id+" wurden aktualisiert.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
