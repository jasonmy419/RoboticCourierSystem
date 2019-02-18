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
			
			sql = "CREATE TABLE	users (" 
				 +"user_id VARCHAR(255) NOT NULL,"
				 +"password VARCHAR(255) NOT NULL,"
				 +"first_name VARCHAR(255),"
				 +"last_name VARCHAR(255),"
				 + "address VARCHAR(255),"
				 + "zipcode VARCHAR(255)"
				 +")";
			
			statement.executeUpdate(sql);
			
			sql = "INSERT INTO users VALUES('1111', '3229c1097c00d497a0fd282d586be050', 'John', 'Smith', '3001 S. Michigan Ave',"
					+ "60616)";
			statement.executeUpdate(sql);
			
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
