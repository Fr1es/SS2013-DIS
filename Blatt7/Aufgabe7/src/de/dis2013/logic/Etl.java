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
		loadFact();
		
		System.out.println(" -- de.dis.2013.logic.Etl END full load --");
	}
	
	/**
	 * Checks if a day exists in DimDay, if not it will be inserted
	 * @day format must be "dd.mm.yyyy"!!!!
	 */
	private void insertDay(String day) {
		try {
			String sqlSelect = "SELECT COUNT(*) as NO FROM DIMDAY d WHERE d.DAYID = ?";
			PreparedStatement stm = DWH.prepareStatement(sqlSelect);
			stm.setString(1, day);
			ResultSet rs = stm.executeQuery();
			rs.next();
			
			if (rs.getInt("NO") == 0) { //wenn der tag noch nicht existiert, muss er erstellt werden:
				String[] dayParts = day.split("\\.");
				
				if (dayParts.length == 3) {//wenn es ein gültiger Jahres Wert ist:
					//calc quarter:
					int month = Integer.parseInt(dayParts[1]); 
					int quarter = ((month-1)/3)+1; //((month-1)/3)+1 
					
					String sqlInsert = "INSERT INTO DIMDAY (DAYID, MONTH, QUARTER, YEAR) values (?,?,?,?)";
					PreparedStatement upStatement = DWH.prepareStatement(sqlInsert);
					upStatement.setString(1, day); //dayID
					upStatement.setString(2, dayParts[1]+"/"+dayParts[2]); //month "mm/yyyy"
					upStatement.setString(3, "Q"+quarter+", "+dayParts[2]); //quarter "Q1, yyyy"
					upStatement.setString(4, dayParts[2]); //year
					upStatement.execute();
					upStatement.close();		
				}
			}
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
	public void loadFact() {
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
			
//			System.out.println("shopMap: "+shopMap.size()+"; articleMap: "+articleMap.size());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
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
			PreparedStatement upStatement;
			
			String line = br.readLine(); //skip first line
			line = br.readLine();
			
			int index = 0;
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
				
//				System.out.println("shopMap: "+shopMap.get(CSVshop)+"; articleMap: "+articleMap.get(CSVarticle));
				
				if ((shopMap.get(CSVshop)) != null && (articleMap.get(CSVarticle) != null)) {					
					//insert day if needed:
					insertDay(CSVdate);
					
					upStatement = DWH.prepareStatement(sqlInsert);
					upStatement.setString(1, CSVdate); //date
					upStatement.setInt(2, shopMap.get(CSVshop)); //shopID
					upStatement.setInt(3, articleMap.get(CSVarticle)); //articleID
					upStatement.setInt(4, CSVsold);
					upStatement.setInt(5, turnover);
					upStatement.addBatch();
					
					index++;
					if (index%100 == 0) {
						upStatement.executeBatch();
						upStatement.close();
						System.out.println("Batch executed (index == "+index+")");
					}
				}
				
				
//				String sqlInsert = "INSERT INTO DIMDAY (DAYID, MONTH, QUARTER, YEAR) values (?,?,?,?)";
//				PreparedStatement upStatement = DWH.prepareStatement(sqlInsert);
//				upStatement.setString(1, day); //dayID
//				upStatement.setString(2, dayParts[1]+"/"+dayParts[2]); //month "mm/yyyy"
//				upStatement.setString(3, "Q"+quarter+", "+dayParts[2]); //quarter "Q1, yyyy"
//				upStatement.setString(4, dayParts[2]); //year
//				upStatement.execute();
//				upStatement.close();		
				
				line = br.readLine();
			}
			
			br.close();
			System.out.println("de.dis2013.logic.Etl - fact load from sucessfully finished");
		} catch(FileNotFoundException e) {
			System.err.println("FILE NOT FOUND ! ! !");
		} catch(IOException e) {
			e.printStackTrace();
		} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		

	}

}
