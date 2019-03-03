package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

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

			sql = "DROP TABLE IF EXISTS address";
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

			// Create new tables
			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL," 
					+ "last_name VARCHAR(255),"
					+ "first_name VARCHAR(255),"
					+ "address_id VARCHAR(255),"
					+ "zipcode VARCHAR(255),"
					+ "coupon INT,"
					+ "PRIMARY KEY (user_id)"
					+ ")";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE address ("
					+ "address_id VARCHAR(255) NOT NULL,"
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "street_num VARCHAR(255)," 
					+ "street_name VARCHAR(255),"
					+ "city VARCHAR(255),"
					+ "state VARCHAR(255),"
					+ "PRIMARY KEY (address_id)"
					+ ")";
			statement.executeUpdate(sql);

			sql = "INSERT INTO users VALUE('0xDEADBEEF', 'admin', 'EMANON', 'ENIGMA', '0x7FFF', 60616, 5)";
			statement.executeUpdate(sql);
			sql = "INSERT INTO users VALUE('admin', '6a3cbac6fbb879aa9a03a6c552483b37', 'admin', 'admin', '0x7FFF', 60616, 3)";
			statement.executeUpdate(sql);
			
			sql = "INSERT INTO address VALUES('11', '167','32', 'angle st', 'LA', 'CA')";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE stations ("
					+ "station_id VARCHAR(255) NOT NULL,"
					//					+ "courier_num_air INT,"
					//					+ "courier_num_bot INT,"
					//					+ "coord VARCHAR(255) NOT NULL,"
					+ "station_lat	DOUBLE NOT NULL,"
					+ "station_lon	DOUBLE NOT NULL,"
					+ "street_number VARCHAR(255),"
					+ "street_name VARCHAR(255),"
					+ "city VARCHAR(255),"
					// FIXME
					+ "PRIMARY KEY (station_id)"
					+ ")";

			// Hardcode the stations in SD & SF
			statement.executeUpdate(sql);
//			sql = "INSERT INTO stations VALUES('11', 32.8346176, -117.1597783, 7373, 'Convoy Ct','San Diego')";
//			statement.executeUpdate(sql);
//			sql = "INSERT INTO stations VALUES('22', 32.8769189, -117.1849218, 5716,'Miramar Rd','San Diego')";
//			statement.executeUpdate(sql);
//			sql = "INSERT INTO stations VALUES('33', 32.8205483, -117.2250157, 4605,'Morena Blvd','San Diego')";
//			statement.executeUpdate(sql);
			sql = "INSERT INTO stations VALUES('44', 37.760370, -122.434826, 531, 'Castro Street','San Francisco')";
			statement.executeUpdate(sql);
			sql = "INSERT INTO stations VALUES('55', 37.796645, -122.423272, 2260,'Van Ness Avenue','San Francisco')";
			statement.executeUpdate(sql);
			sql = "INSERT INTO stations VALUES('66', 37.755327, -122.489248, 1706,'31st Avenue','San Francisco')";
			statement.executeUpdate(sql);


			//
			sql = "CREATE TABLE couriers ("
					+ "courier_id VARCHAR(255) NOT NULL,"
					+ "type VARCHAR(255) NOT NULL,"
					//					+ "price FLOAT,"
					+ "station_id VARCHAR(255) NOT NULL,"
					//					+ "order VARCHAR(255) NOT NULL,"
					+ "time TIMESTAMP NOT NULL,"
					// FIXME
					+ "PRIMARY KEY (courier_id)"
					//					+ "FOREIGN KEY (station_id) REFERENCES items(station_id)"
					+ ")";

			// Hardcode the couriers in SD
			statement.executeUpdate(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			Timestamp current_time = new Timestamp((new Date()).getTime());
			
			sql = "INSERT INTO couriers VALUES('441', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();

			sql = "INSERT INTO couriers VALUES('442', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('451', 'Air', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('443', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();

			sql = "INSERT INTO couriers VALUES('444', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('452', 'Air', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('541', 'Robot', '55', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('542', 'Robot', '55', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('543', 'Robot', '55', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();

			sql = "INSERT INTO couriers VALUES('655', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('643', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('656', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('644', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('657', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('645', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('658', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('646', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			// insert a future timestamp
			Timestamp s = new Timestamp((new Date()).getTime() + 3000000);
			sql = "INSERT INTO couriers VALUES('641', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();

			sql = "INSERT INTO couriers VALUES('651', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('653', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('654', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('642', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();

			// Hardcode the couriers in SF
			sql = "INSERT INTO couriers VALUES('411', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('412', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('421', 'Air', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('511', 'Robot', '55', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, current_time);
			stmt.executeUpdate();

			// insert a future timestamp
			s = new Timestamp((new Date()).getTime() + 400000);
			sql = "INSERT INTO couriers VALUES('415', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('611', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();

			sql = "INSERT INTO couriers VALUES('621', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('416', 'Robot', '44', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();
			
			sql = "INSERT INTO couriers VALUES('612', 'Robot', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();

			sql = "INSERT INTO couriers VALUES('622', 'Air', '66', ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, s);
			stmt.executeUpdate();
			
			sql = "CREATE TABLE	orders (" 
					+"order_id VARCHAR(255) NOT NULL,"
					+"user_id VARCHAR(255) NOT NULL,"
					+"courier_id VARCHAR(255) NOT NULL,"
					+"item_id VARCHAR(255) NOT NULL,"
					+"type VARCHAR(255) NOT NULL,"
					+"start_address_id VARCHAR(255) NOT NULL,"
					+"end_address_id VARCHAR(255) NOT NULL,"
					+"end_time TIMESTAMP NOT NULL,"
					+"route_duration DOUBLE NOT NULL,"
					+"route_distance DOUBLE NOT NULL,"
					+"route_price DOUBLE NOT NULL,"
					+"route_path VARCHAR(255) NOT NULL,"
					+"complete BOOLEAN,"
					+"recommended BOOLEAN"
					+")";
			statement.executeUpdate(sql);

//			Timestamp end = new Timestamp((new Date()).getTime());
//			sql = "INSERT INTO orders VALUES('sfogbwklskansbbvncs012e','123','xyz','111','D',"
//					+ " 'lavieenrose', 'emanon', ?" 
//					+ ", 996.0, 11.25, 29.83, 'k}qcFjushVf@QFABABAF?D?D@RBB@D?J?N?B?n@JLJJ@LDTDNDNDFBFBDBDBFBFBHB',"
//					+ "TRUE, FALSE)";
//			stmt = conn.prepareStatement(sql);
//			stmt.setTimestamp(1, s);
//			stmt.executeUpdate();

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
					+"cvv VARCHAR(255) NOT NULL"
					+")";

			statement.executeUpdate(sql);

			sql = "INSERT INTO payment VALUES('0xDEADBEEF', 'EMANON', 'ENIGMA','xxxx-xxxx-xxxx-xxxx',"
					+ " '3001 S. Michigan Ave', '', 'Chicago', 60616, 'IL', 04, 2018, '907')";
			statement.executeUpdate(sql);

			conn.close();
			System.out.println("Import done successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}