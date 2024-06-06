package com.tyss.RestAssured.genericUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;


public class DatabaseLibrary {

	Connection con;
	/**
	 * This method is used to connect to the database
	 * @throws SQLException
	 */
	public void connectToDB() throws SQLException {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		con = DriverManager.getConnection(IPathConstatns.DBURL, IPathConstatns.DBUSERNAME, IPathConstatns.DBPASSWORD);
		
		
	}
	public void closeDBConnection() throws SQLException {
		con.close();
	}
	
	/**
	 * This method is used to read the Data from DB and validate.
	 * @param query
	 * @param colmIndex
	 * @param expData
	 * @return
	 * @throws SQLException
	 */
	
	public String readDataFromDBAndVerify(String query, int colmIndex, String expData) throws SQLException {
		ResultSet result = con.createStatement().executeQuery(query);
		boolean flag=false;
		while(result.next()) {
			String actualD = result.getString(colmIndex);
			if(actualD.contains(expData)) {
				flag=true;
				break;
			}
		}
		if(flag) {
			System.out.println("Data is present");
			return expData;
		}
		else {
			System.out.println("Data is not present");
			return "";
		}
	}
	public void writeDataIntoDB(String query) throws SQLException {
		con.createStatement().executeUpdate(query);
	}
}
