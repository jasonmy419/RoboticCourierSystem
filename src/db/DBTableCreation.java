package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Run this application to reset database schema
 */
public class DBTableCreation {

	public static void main(String[] args) {
		
		try {
			// Connect to MySQL.
			System.out.println("Connecting to " + DBUtility.URL);
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			Connection conn = DriverManager.getConnection(DBUtility.URL);
			
			if (conn == null) {
				return;
			}
			
			// Drop tables in case they exist
			Statement statement = conn.createStatement();
			
			String sql = "DROP TABLE IF EXISTS users";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS users_end";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS stations";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS couriers";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS orders";
			statement.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS items";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS payment";
			statement.executeUpdate(sql);
			
//			sql = "CREATE TABLE	users (" 
//				 +"user_id VARCHAR(255) NOT NULL,"
//				 +"password VARCHAR(255) NOT NULL,"
//				 +"first_name VARCHAR(255),"
//				 +"last_name VARCHAR(255),"
//				 + "address VARCHAR(255),"
//				 + "zipcode VARCHAR(255)"
//				 +")";
			
			sql = "CREATE TABLE	payment (" 
					 +"user_id VARCHAR(255) NOT NULL,"
					 +"last_name VARCHAR(255) NOT NULL,"
					 +"first_name VARCHAR(255) NOT NULL,"
					 +"card_number VARCHAR(255) NOT NULL,"
					 +"address_line1 VARCHAR(255) NOT NULL,"
					 +"address_line2 VARCHAR(255),"
					 +"city VARCHAR(255) NOT NULL,"
					 +"zipcode INT NOT NULL,"
					 +"state VARCHAR(255) NOT NULL,"
					 +"month INT NOT NULL,"
					 +"year INT NOT NULL,"
					 +"cvv INT NOT NULL"
					 +")";
			
			
			statement.executeUpdate(sql);
			
			sql = "INSERT INTO payment VALUES('2233', 'wu', 'sicheng','xxxx-xxxx-xxxx-xxxx',"
					+ " '3001 S. Michigan Ave', '', 'Chicago', 60616, 'IL', 04, 2018, 907)";
			statement.executeUpdate(sql);
			
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
