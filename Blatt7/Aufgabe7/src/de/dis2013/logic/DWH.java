package de.dis2013.logic;

import java.sql.*;

import de.dis2013.db.DWHConnectionManager;

public class DWH {
	private final Connection DWH;
	private  String cubeSql;
	/**
	 * Constructor
	 */
	public DWH() {
		DWH = DWHConnectionManager.getInstance().getConnection();
		cubeSql = "select ?, ?, ?, cast( sum(f.SOLD) as DOUBLE) as Sold, round(sum( cast(f.TURNOVER as DOUBLE) /1000000)/100, 3) as TurnoverMIO "+
					"from vsisp17.fact f "+ 
						"join vsisp17.DIMSHOP s on (f.SHOPID = s.SHOPID) "+
							"join vsisp17.DIMARTICLE a on (f.ARTICLEID = a.ARTICLEID) "+
								"join vsisp17.DIMDAY d on (f.DAYID = d.DAYID) "+
									"group by cube (?, ?, ?) "+
										"order by ?, ?, ? ";
//		cubeSql = "select s.NAME, d.QUARTER, a.PRODUCTFAMILY, cast( sum(f.SOLD) as DOUBLE) as Anz, round(sum( cast(f.TURNOVER as DOUBLE) /1000000)/100, 3) as Verkauft_in_MIO "+
//"from vsisp17.fact f "+ 
//"join vsisp17.DIMSHOP s on (f.SHOPID = s.SHOPID) "+
//"join vsisp17.DIMARTICLE a on (f.ARTICLEID = a.ARTICLEID) "+
//"join vsisp17.DIMDAY d on (f.DAYID = d.DAYID) "+
//"group by cube (s.name, d.quarter, a.PRODUCTFAMILY) "+
//"order by s.name, d.quarter, a.PRODUCTFAMILY";
	}
	
	public void dwhCube(String shop, String day, String article) {
		try {
			String cubeSQL = "select s."+shop+", d."+day+", a."+article+", cast( sum(f.SOLD) as DOUBLE) as Sold, round(sum( cast(f.TURNOVER as DOUBLE) /1000000)/100, 3) as TurnoverMIO "+
					"from vsisp17.fact f "+ 
						"join vsisp17.DIMSHOP s on (f.SHOPID = s.SHOPID) "+
							"join vsisp17.DIMARTICLE a on (f.ARTICLEID = a.ARTICLEID) "+
								"join vsisp17.DIMDAY d on (f.DAYID = d.DAYID) "+
									"group by cube (s."+shop+", d."+day+", a."+article+") "+
										"order by s."+shop+", d."+day+", a."+article+" ";
			
//			PreparedStatement upStatement = DWH.prepareStatement(cubeSql);
//			upStatement.setString(1, "s."+shop);
//			upStatement.setString(2, "d."+day);
//			upStatement.setString(3, "a."+article);
//			upStatement.setString(4, "s."+shop);
//			upStatement.setString(5, "d."+day);
//			upStatement.setString(6, "a."+article);
//			upStatement.setString(7, "s."+shop);
//			upStatement.setString(8, "d."+day);
//			upStatement.setString(9, "a."+article);
//			
			
//			System.out.println("executing");
			Statement stm = DWH.createStatement();
			ResultSet rs = stm.executeQuery(cubeSQL);
			//output:
			dwhOutput(rs, shop, day, article);
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void dwhOutput(ResultSet rs, String shop, String day, String article) {
		System.out.println();
		System.out.printf("%20s\t%20s\t%20s\t%20s\t%20s\n", shop,day, article, "Sold", "Turnover_(MIO)");
		try {
			while (rs.next()) {
				String[] lineOutput = { rs.getString(1), rs.getString(2), rs.getString(3), ""+rs.getDouble(4), ""+rs.getDouble(5)};
				
				System.out.printf("%20s\t%20s\t%20s\t%20s\t%20s\n", lineOutput);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
