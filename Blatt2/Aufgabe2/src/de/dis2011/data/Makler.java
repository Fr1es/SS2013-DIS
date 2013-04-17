package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2011.FormUtil;
import de.dis2011.data.DB2ConnectionManager;

/**
 * Makler-Bean
 * 
 * Beispiel-Tabelle:
 * CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
 * name varchar(255),
 * address varchar(255),
 * login varchar(40) UNIQUE,
 * password varchar(40));
 */
public class Makler {
	private String name;
	private String address;
	private String login = null;
	private String password;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Makler load(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			
			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM makler WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Makler ts = new Makler();
				ts.setName(rs.getString("name"));
				ts.setAddress(rs.getString("address"));
				ts.setLogin(rs.getString("login"));
				ts.setPassword(rs.getString("password"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		try {
			Statement stm = con.createStatement();
			
			ResultSet rs = stm.executeQuery("Select * FROM makler WHERE login = '"+ getLogin() +"'");

			
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (!rs.next()) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO makler(name, adresse, login, passwort) VALUES (?, ?, ?, ?)";
				
				
				PreparedStatement pstmt = con.prepareStatement(insertSQL);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getLogin());
				pstmt.setString(4, getPassword());
				pstmt.executeUpdate();

				pstmt.close();
				System.out.println("Der Markler mit dem Login "+getLogin()+" wurde erstellt.");
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE makler SET name = ?, adresse = ?, passwort = ? WHERE login = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getPassword());
				pstmt.setString(4, getLogin());

				pstmt.executeUpdate();

				pstmt.close();
				System.out.println("Die Eintraege des Marklers mit dem Login "+getLogin()+" wurden aktualisiert.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Löscht einen Makler mit dem Login "id"
	 * @param id
	 */
	public void delete(String id)
	{
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		try {			
			String SQLString = "DELETE FROM makler WHERE login = ?";
			
			PreparedStatement pstmt = con.prepareStatement(SQLString);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			System.out.println("Der Makler mit dem Login "+id+" wurde gelöscht");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Anmeldung eines Maklers mit Login und Passwort
	 * @return true wenn Anmeldung erfolgreich war, sonst false
	 */
	public boolean loginMakler()
	{
		String loginAnmelder;
		String passwortAnmelder;
		
		System.out.println("- Maklerlogin -");
		System.out.println("Bitte geben Sie Ihr Login und Passwort an.");
		
		loginAnmelder = FormUtil.readString("Login");
		passwortAnmelder = FormUtil.readString("Passwort");
		
		//Abfrage des Logins/PW auf der DB:
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		try {
			Statement stm = con.createStatement();
			
			ResultSet rs = stm.executeQuery("Select passwort FROM makler WHERE login = '"+ loginAnmelder +"'");
			
			while(rs.next()) {
				String DBpw = rs.getString("passwort");
				
				if(DBpw.equals(passwortAnmelder)) {
					return true;
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
