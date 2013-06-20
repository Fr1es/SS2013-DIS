package de.dis2013.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2013.db.DWHConnectionManager;
import de.dis2013.db.STAMMDATENConnectionManager;

/**
 * Starts the ETL-Process
 * @author Björn Fries
 *
 */
public class Etl {
	private final Connection DWH;
	private final Connection STAMM;
	
	public Etl() {
		// Build Connections:
		DWH = DWHConnectionManager.getInstance().getConnection();
		STAMM = STAMMDATENConnectionManager.getInstance().getConnection();
	}
	
	/**
	 * Starts the ETL-process (FULL load!)
	 */
	public void startFullLoad() {
		System.out.println(" -- de.dis2013.logic.Etl START full load --");
		//masterdata
		loadDimShop();
		loadDimArticle();
		
		
		System.out.println(" -- de.dis.2013.logic.Etl END full load --");
	}
	
	/**
	 * ETL for DimDay
	 */
	private void loadDimDay() {
		//TODO
		//
		
		
//		Statement stm = con.createStatement();
//		
//		System.out.println("--  Mietvertraege:  --");
//		ResultSet rs = stm.executeQuery("Select VERTRAGSNUMMER,DATUM,ORT,MIETBEGINN,DAUER,NEBENKOSTEN,PID,IMMOID FROM mietvertrag");
//		while(rs.next()){
//			int vnr = rs.getInt("VERTRAGSNUMMER");
//			//datum?
//			String ort = rs.getString("ORT");
//			//Mietbeginn?
//			int dauer = rs.getInt("DAUER");
//			//Nebenkosten?
//			int pid = rs.getInt("PID");
//			int immoId = rs.getInt("IMMOID");
//
//		Statement stm = con.createStatement();
//		
//		ResultSet rs = stm.executeQuery("Select * FROM makler WHERE login = '"+ getLogin() +"'");
//
//		
//		// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
//		if (!rs.next()) {
//			// Achtung, hier wird noch ein Parameter mitgegeben,
//			// damit spC$ter generierte IDs zurC<ckgeliefert werden!
//			String insertSQL = "INSERT INTO makler(name, adresse, login, passwort) VALUES (?, ?, ?, ?)";
//			
//			
//			PreparedStatement pstmt = con.prepareStatement(insertSQL);
//
//			// Setze Anfrageparameter und fC<hre Anfrage aus
//			pstmt.setString(1, getName());
//			pstmt.setString(2, getAddress());
//			pstmt.setString(3, getLogin());
//			pstmt.setString(4, getPassword());
//			pstmt.executeUpdate();
//
//			pstmt.close();
		
	}
	
	/**
	 * ETL for DimShop
	 */
	private void loadDimShop() {
		System.out.println("de.dis2013.logic.Etl - load dimension shop - starting");

		String sqlSelect = "SELECT sho.SHOPID as SHOPID, sho.NAME as NAME, sta.NAME as TOWN, reg.NAME as REGION, lan.NAME as COUNTRY "+
						"FROM SHOPID sho "+
							"join STADTID sta on sho.STADTID = sta.STADTID "+
								"join REGIONID reg on sta.REGIONID = reg.REGIONID "+
									"join LANDID lan on reg.LANDID = lan.LANDID";
		String sqlInsert = "INSERT INTO DIMSHOP (SHOPID, NAME, TOWN, REGION, COUNTRY) values (?,?,?,?,?)";
		
		try {
			int shopID;
			String shopName;
			String townName;
			String regionName;
			String countryName;
			PreparedStatement upStatement;
			
			Statement stm = STAMM.createStatement();
			ResultSet rs = stm.executeQuery(sqlSelect);
			
			while(rs.next()) {
				//READ values
				shopID = rs.getInt("SHOPID");
				shopName = rs.getString("NAME");
				townName = rs.getString("TOWN");
				regionName = rs.getString("REGION");
				countryName = rs.getString("COUNTRY");
				
				//WRITE values
				upStatement = DWH.prepareStatement(sqlInsert);
				upStatement.setInt(1, shopID);
				upStatement.setString(2, shopName);
				upStatement.setString(3, townName);
				upStatement.setString(4, regionName);
				upStatement.setString(5, countryName);
				upStatement.execute();
				upStatement.close();
			}
			
			System.out.println("de.dis2013.logic.Etl - load dimension shop - successfully finished");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ETL for DimArticle
	 */
	private void loadDimArticle() {
		System.out.println("de.dis2013.logic.Etl - load dimension article - starting");
		
		String sqlSelect = "SELECT art.ARTICLEID as ARTICLEID, art.NAME as NAME, gru.NAME as PRODUCTGROUP, fam.NAME as PRODUCTFAMILY, kat.NAME as PRODUCTCATEGORY "+
						"FROM ARTICLEID art "+
							"join PRODUCTGROUPID gru on art.PRODUCTGROUPID = gru.PRODUCTGROUPID "+
								"join PRODUCTFAMILYID fam on gru.PRODUCTFAMILYID = fam.PRODUCTFAMILYID "+
									"join PRODUCTCATEGORYID kat on kat.PRODUCTCATEGORYID = fam.PRODUCTCATEGORYID";
		String sqlInsert = "INSERT INTO DIMARTICLE (ARTICLEID, NAME, PRODUCTGROUP, PRODUCTFAMILY, PRODUCTCATEGORY) values (?,?,?,?,?)";
		
		try {
			int articleID;
			String articleName;
			String productGroup;
			String productFamily;
			String productCategory;
			PreparedStatement upStatement;
			
			Statement stm = STAMM.createStatement();
			ResultSet rs = stm.executeQuery(sqlSelect);
			
			while(rs.next()) {
				//READ values
				articleID = rs.getInt("ARTICLEID");
				articleName = rs.getString("NAME");
				productGroup = rs.getString("PRODUCTGROUP");
				productFamily = rs.getString("PRODUCTFAMILY");
				productCategory = rs.getString("PRODUCTCATEGORY");
				
				//WRITE values
				upStatement = DWH.prepareStatement(sqlInsert);
				upStatement.setInt(1, articleID);
				upStatement.setString(2, articleName);
				upStatement.setString(3, productGroup);
				upStatement.setString(4, productFamily);
				upStatement.setString(5, productCategory);
				upStatement.execute();
				upStatement.close();
			}
			System.out.println("de.dis2013.logic.Etl - load dimension article - successfully finished");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Clears the whole DWH (should be done before a full loads starts
	 */
	public void clearDWH() {
		System.out.println("de.dis2013.logic.Etl - clearing DWH start");
		try {
			PreparedStatement deleteStatement;
			
			//delete fact
			deleteStatement = DWH.prepareStatement("DELETE FROM FACT");
			deleteStatement.executeUpdate();
			System.out.println("de.dis2013.logic.Etl - fact cleard");
			
			//delete DimDay
			deleteStatement = DWH.prepareStatement("DELETE FROM DIMDAY");
			deleteStatement.executeUpdate();
			System.out.println("de.dis2013.logic.Etl - DimDay cleard");
			
			//delete DimShop
			deleteStatement = DWH.prepareStatement("DELETE FROM DIMSHOP");
			deleteStatement.executeUpdate();
			System.out.println("de.dis2013.logic.Etl - DimShop cleard");
			
			//delete DimArticle
			deleteStatement = DWH.prepareStatement("DELETE FROM DIMARTICLE");
			deleteStatement.executeUpdate();
			System.out.println("de.dis2013.logic.Etl - DimArticle cleard");
			
			System.out.println("de.dis2013.logic.Etl - clearing DWH successfully finished");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
