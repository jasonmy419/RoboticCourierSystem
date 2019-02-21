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
			
			
//			// Create new tables
//			sql = "CREATE TABLE users ("
//					+ "user_id VARCHAR(255) NOT NULL,"
//					+ "password VARCHAR(255) NOT NULL" 
//					+ "last_name VARCHAR(255),"
//					+ "first_name VARCHAR(255),"
//					+ "address VARCHAR(255),"
//					+ "zipcode VARCHAR(255),"
//					// FIXME
////					+ "PRIMARY KEY (user_id)"
//					+ ")";
//			statement.executeUpdate(sql);
//
//			sql = "CREATE TABLE users_end ("
//					+ "user_id VARCHAR(255) NOT NULL,"
//					+ "last_name VARCHAR(255),"
//					+ "first_name VARCHAR(255),"
//					+ "address VARCHAR(255),"
//					+ "zipcode VARCHAR(255),"
//					// FIXME
////					+ "PRIMARY KEY (user_id)"
//					+ ")";
//			statement.executeUpdate(sql);
//			
			sql = "CREATE TABLE stations ("
					+ "station_id VARCHAR(255) NOT NULL,"
//					+ "courier_num_air INT,"
//					+ "courier_num_bot INT,"
//					+ "coord VARCHAR(255) NOT NULL,"
					+ "street_number INT(255),"
					+ "street_name VARCHAR(255),"
					+ "city VARCHAR(255),"
					// FIXME
					+ "PRIMARY KEY (station_id)"
					+ ")";
			statement.executeUpdate(sql);
//
//			sql = "CREATE TABLE couriers ("
//					+ "courier_id VARCHAR(255) NOT NULL,"
//					+ "type VARCHAR(255) NOT NULL,"
//					+ "price FLOAT,"
//					+ "station VARCHAR(255) NOT NULL,"
//					+ "order VARCHAR(255) NOT NULL,"
//					// FIXME
////					+ "PRIMARY KEY (courier_id, station),"
////					+ "FOREIGN KEY (station_id) REFERENCES items(station_id)"
//					+ ")";
//			statement.executeUpdate(sql);
			
//			sql = "CREATE TABLE orders ("
//					+ "order_id VARCHAR(255) NOT NULL,"
//					+ "item_id VARCHAR(255) NOT NULL,"
//					+ "courier_id VARCHAR(255) NOT NULL,"
//					+ "user_id VARCHAR(255) NOT NULL,"
//					+ "user_id_end VARCHAR(255) NOT NULL,"
//					+ "status VARCHAR(255) NOT NULL,"
//					// TODO
//					+ "PRIMARY KEY (order_id)"
//					+ "FOREIGN KEY (station_id) REFERENCES items(station_id)"
//					+ ")";
//			statement.executeUpdate(sql);
			
//			sql = "CREATE TABLE items ("
//					+ "item_id VARCHAR(255) NOT NULL,"
//					+ "weight FLOAT,"
//					// FIXME
////					+ "PRIMARY KEY (item_id)"
//					+ ")";
//			statement.executeUpdate(sql);

			// TODO
//			sql = "INSERT INTO orders VALUES('123k11','Delivered')";
//			statement.executeUpdate(sql);
//			sql = "INSERT INTO orders VALUES('123k1','In Transit')";
//			statement.executeUpdate(sql);
			
			sql = "INSERT INTO stations VALUES('11', 7373,'Concoy Ct','San Diego')";
			statement.executeUpdate(sql);
			sql = "INSERT INTO stations VALUES('22', 5716,'Miramar Rd','San Diego')";
			statement.executeUpdate(sql);
			sql = "INSERT INTO stations VALUES('33', 4605,'Morena Blvd','San Diego')";
			statement.executeUpdate(sql);
			
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
			
			
			sql = "CREATE TABLE	orders (" 
					 +"user_id VARCHAR(255) NOT NULL,"
					 +"courier_id VARCHAR(255) NOT NULL,"
					 +"item_id VARCHAR(255) NOT NULL,"
					 +"type VARCHAR(255) NOT NULL,"
					 +"start_street_number VARCHAR(255) NOT NULL,"
					 +"start_street_name VARCHAR(255) NOT NULL,"
					 +"start_city VARCHAR(255) NOT NULL,"
					 +"end_street_number VARCHAR(255) NOT NULL,"
					 +"end_street_name VARCHAR(255) NOT NULL,"
					 +"end_city VARCHAR(255) NOT NULL,"
					 +"route_id VARCHAR(255) NOT NULL,"
					 +"status VARCHAR(255) NOT NULL,"
					 +"route_duration DOUBLE NOT NULL,"
					 +"route_distance DOUBLE NOT NULL,"
					 +"route_price DOUBLE NOT NULL,"
					 +"route_path VARCHAR(255) NOT NULL"
					 +")";
			statement.executeUpdate(sql);
			
			sql = "INSERT INTO orders VALUES('123','xyz','111','D', '3869', 'Miramar St',"
					+ " 'La Jolla', '4609', 'Convoy St', 'San Diego',"
					+ "'ahdjbbkvksd','TRANSIT',"
				+ "996.0, 11.25, 29.83, 'k}qcFjushVf@QFABABAF?D?D@RBB@D?J?N?B?n@JLJJ@LDTDNDNDFBFBDBDBFBFBHB')";
			statement.executeUpdate(sql);

	
			conn.close();
			System.out.println("Import done successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
