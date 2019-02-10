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
			
			// Create new tables
			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL" 
					+ "last_name VARCHAR(255),"
					+ "first_name VARCHAR(255),"
					+ "address VARCHAR(255),"
					+ "zipcode VARCHAR(255),"
					// FIXME
//					+ "PRIMARY KEY (user_id)"
					+ ")";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE users_end ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "last_name VARCHAR(255),"
					+ "first_name VARCHAR(255),"
					+ "address VARCHAR(255),"
					+ "zipcode VARCHAR(255),"
					// FIXME
//					+ "PRIMARY KEY (user_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE stations ("
					+ "station_id VARCHAR(255) NOT NULL,"
					+ "courier_num_air INT,"
					+ "courier_num_bot INT,"
					+ "coord VARCHAR(255) NOT NULL,"
					// FIXME
//					+ "PRIMARY KEY (station_id)"
					+ ")";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE couriers ("
					+ "courier_id VARCHAR(255) NOT NULL,"
					+ "type VARCHAR(255) NOT NULL,"
					+ "price FLOAT,"
					+ "station VARCHAR(255) NOT NULL,"
					+ "order VARCHAR(255) NOT NULL,"
					// FIXME
//					+ "PRIMARY KEY (courier_id, station),"
//					+ "FOREIGN KEY (station_id) REFERENCES items(station_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE orders ("
					+ "order_id VARCHAR(255) NOT NULL,"
					+ "item_id VARCHAR(255) NOT NULL,"
					+ "courier_id VARCHAR(255) NOT NULL,"
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "user_id_end VARCHAR(255) NOT NULL,"
					+ "status VARCHAR(255) NOT NULL,"
					// TODO
//					+ "PRIMARY KEY (item_id, courier),"
//					+ "FOREIGN KEY (station_id) REFERENCES items(station_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE items ("
					+ "item_id VARCHAR(255) NOT NULL,"
					+ "weight FLOAT,"
					// FIXME
//					+ "PRIMARY KEY (item_id)"
					+ ")";
			statement.executeUpdate(sql);

			// TODO
			
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
