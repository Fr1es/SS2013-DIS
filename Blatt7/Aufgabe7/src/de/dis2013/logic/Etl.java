package de.dis2013.logic;

import java.sql.Connection;
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
	}
	
	/**
	 * ETL for DimShop
	 */
	private void loadDimShop() {
		//TODO
		String sql = "SELECT sho.NAME as NAME, sta.NAME as TOWN, reg.NAME as REGION, lan.NAME as COUNTRY "+
						"FROM SHOPID sho "+
							"join STADTID sta on sho.STADTID = sta.STADTID "+
								"join REGIONID reg on sta.REGIONID = reg.REGIONID "+
									"join LANDID lan on reg.LANDID = lan.LANDID";
		
		try {
			String shopName;
			String townName;
			String regionName;
			String countryName;
			
			Statement stm = STAMM.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				shopName = rs.getString("NAME");
				townName = rs.getString("TOWN");
				regionName = rs.getString("REGION");
				countryName = rs.getString("COUNTRY");
				
				System.out.println(shopName+townName+regionName+countryName);
				
				

			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * ETL for DimArticle
	 */
	private void loadDimArticle() {
		//TODO
		String sql = "SELECT art.NAME as NAME, gru.NAME as PRODUCTGROUP, fam.NAME as PRODUCTFAMILY, kat.NAME as PRODUCTCATEGORY "+
						"FROM ARTICLEID art "+
							"join PRODUCTGROUPID gru on art.PRODUCTGROUPID = gru.PRODUCTGROUPID "+
								"join PRODUCTFAMILYID fam on gru.PRODUCTFAMILYID = fam.PRODUCTFAMILYID "+
									"join PRODUCTCATEGORYID kat on kat.PRODUCTCATEGORYID = fam.PRODUCTCATEGORYID";
		
		try {
			String articleName;
			String productGroup;
			String productFamily;
			String productCategory;
			
			Statement stm = STAMM.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				articleName = rs.getString("NAME");
				productGroup = rs.getString("PRODUCTGROUP");
				productFamily = rs.getString("PRODUCTFAMILY");
				productCategory = rs.getString("PRODUCTCATEGORY");
				
				System.out.println(articleName+productGroup+productFamily+productCategory);
				
				
				
				// SQLERRMC: SHOPID;.NAME as COUNTRYFROM;FROM
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Clears the whole DWH (should be done before a full loads starts
	 */
	public void clearDWH() {
		System.out.println("de.dis.2013.logic.Etl - clearing DWH start");
		//TODO
		
		System.out.println("de.dis.2013.logic.Etl - clearing DWH end");
	}

}
