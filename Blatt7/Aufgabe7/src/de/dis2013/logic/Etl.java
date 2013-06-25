package de.dis2013.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
		loadDimDay();
		loadFact();
		
		System.out.println(" -- de.dis.2013.logic.Etl END full load --");
	}
	
	/**
	 * Checks if a day exists in DimDay, if not it will be inserted
	 * @day format must be "dd.mm.yyyy"!!!!
	 */
	private void loadDimDay() {
		System.out.println("de.dis2013.logic.Etl - load dimension day - starting");

		insertMonth(31, 1, 2013);
		insertMonth(28, 2, 2013);
		insertMonth(31, 3, 2013);
		insertMonth(30, 4, 2013);
		insertMonth(31, 5, 2013);
			
		System.out.println("de.dis2013.logic.Etl - load dimension day - successfully finshed");
	}
	
	/**
	 * Helper Class for loadDimDay. inserts a whole month to the table dimDay
	 */
	private void insertMonth(int dayMax, int month, int year) {
		String sqlInsert = "INSERT INTO DIMDAY (DAYID, MONTH, QUARTER, YEAR) values (?,?,?,?)";
		String monthAsString = (month < 10) ? "0"+month : ""+month;
		int quarter = ((month-1)/3)+1;
		String yearAsString = ""+year;
		String quarterAsString = "Q"+quarter+", "+yearAsString;
		
		try {
			PreparedStatement upStatement = DWH.prepareStatement(sqlInsert);
			
			for (int day = 1; day<=dayMax; day++) {
				String dayAsString = (day < 10) ? "0"+day : ""+day;
				String dayId = dayAsString+"."+monthAsString+"."+yearAsString;
				
				upStatement.setString(1, dayId);
				upStatement.setString(2, monthAsString);
				upStatement.setString(3, quarterAsString);
				upStatement.setString(4, yearAsString);
				upStatement.addBatch();
			}
			
			upStatement.executeBatch();
			DWH.commit();
			upStatement.close();
			
			System.out.println("de.dis2013.logic.Etl - added month "+month);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
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
	
	/**
	 * loads the fact Table from the csv-File
	 */
	private void loadFact() {
		System.out.println("de.dis2013.logic.Etl - fact load from csv start");
		HashMap<String, Integer> shopMap = new HashMap<String, Integer>();
		HashMap<String, Integer> articleMap = new HashMap<String, Integer>();

		//load the Maps from the DWH
		try {
			String sqlShop = "SELECT SHOPID, NAME FROM DIMSHOP";
			String sqlArticle = "SELECT ARTICLEID, NAME FROM DIMARTICLE";
			
			//load shopMap
			Statement statementShop = DWH.createStatement();
			ResultSet rs = statementShop.executeQuery(sqlShop);
			while(rs.next()) {
				shopMap.put(rs.getString("NAME"), rs.getInt("SHOPID"));				
			}
			
			//load articleMap
			Statement statementArticle = DWH.createStatement();
			rs = statementArticle.executeQuery(sqlArticle);
			while(rs.next()) {
				articleMap.put(rs.getString("NAME"), rs.getInt("ARTICLEID"));				
			}
			
//			System.out.println("shopMap: "+shopMap.size()+"; articleMap: "+articleMap.size()); //debug
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		int index = 0;
		String line = null;
		//write into the fact and dimDay table
		try {
			FileReader fr = new FileReader("src\\de\\dis2013\\db\\sales.csv");
			BufferedReader br = new BufferedReader(fr);
			String[] parts;
			String CSVdate;
			String CSVshop;
			String CSVarticle;
			int CSVsold;
			String[] CSVturnover;
			int turnover;
			String sqlInsert = "INSERT INTO FACT (DAYID,SHOPID,ARTICLEID,SOLD,TURNOVER) VALUES (?,?,?,?,?)";
			DWH.setAutoCommit(false);
			PreparedStatement upStatement = DWH.prepareStatement(sqlInsert);
			
			br.readLine(); //skip first line
			line = br.readLine();
			

			while(line != null) {
				//read lines
				parts = line.split("\\;");
				CSVdate = parts[0];
				CSVshop = parts[1];
				CSVarticle = parts[2];
				CSVsold = Integer.parseInt(parts[3]);
				//convert turnover from xx,xx to xxxx (int)
				CSVturnover = parts[4].split("\\,");
				turnover = (Integer.parseInt(CSVturnover[0]) * 100 ) + (Integer.parseInt(CSVturnover[1]));
				
//				System.out.println("shopMap: "+shopMap.get(CSVshop)+"; articleMap: "+articleMap.get(CSVarticle)); //debug
				
				if ((shopMap.get(CSVshop)) != null && (articleMap.get(CSVarticle) != null)) {
					

					upStatement.setString(1, CSVdate); //date
					upStatement.setInt(2, shopMap.get(CSVshop)); //shopID
					upStatement.setInt(3, articleMap.get(CSVarticle)); //articleID
					upStatement.setInt(4, CSVsold);
					upStatement.setInt(5, turnover);
					upStatement.addBatch();

//					System.out.println("addBatch(): "+CSVdate+shopMap.get(CSVshop)+articleMap.get(CSVarticle)+CSVsold+turnover);
					
					index++;
					if (index%1000 == 0) {
						upStatement.executeBatch();
						DWH.commit();
						upStatement.clearBatch();
						System.out.println("Batch executed (index == "+index+")");
					}
				}
				
				
				line = br.readLine();
			}
			
			//Last insert:
			upStatement.executeBatch();
			DWH.commit();
			upStatement.clearBatch();
			System.out.println("Batch executed (index == "+index+")");
			
			//cleaning up:
			upStatement.close();
			DWH.setAutoCommit(true);
			
			br.close();
			System.out.println("de.dis2013.logic.Etl - fact load from sucessfully finished");
		} catch(FileNotFoundException e) {
			System.err.println("FILE NOT FOUND ! ! !");
		} catch(IOException e) {
			e.printStackTrace();
		} 
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("index: "+index);
			System.out.println("Error at line: "+line);
		}
		

	}

}
