package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

public class DBConnection {

	private Connection conn;	

	public DBConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(DBUtility.URL);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from /src/db/DBConnection -> " + e.getMessage());
		}
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setPaymentInfo(String userID, JSONObject input) {
	
		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setPaymentInfo");
		}

		try {
			
			 String sql = "SELECT address_line1 FROM payment WHERE user_id = ? AND card_number = ? ";
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 stmt.setString(1,userID);
			 stmt.setString(2,input.getString("card_number"));
			 ResultSet rs = stmt.executeQuery();
			 
			 String cardAddress = "";
			 
			 while (rs.next()) {
				 cardAddress = rs.getString("address_line1");
			 }
			 
			 if (cardAddress == null || cardAddress.length() == 0) {
				 sql = "INSERT IGNORE INTO payment VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		   		 PreparedStatement ps = conn.prepareStatement(sql);
		   		 ps.setString(1, userID);
		   		 ps.setString(2, input.getString("last_name"));
		   		 ps.setString(3, input.getString("first_name"));
		   		 ps.setString(4, input.getString("card_number"));
		   		 ps.setString(5, input.getString("address_line1"));
		   		 ps.setString(6, input.getString("address_line2"));
		   		 ps.setString(7, input.getString("city"));
		   		 ps.setInt(8, input.getInt("zipcode"));
		   		 ps.setString(9, input.getString("state"));
		   		 ps.setInt(10, input.getInt("month"));
		   		 ps.setInt(11, input.getInt("year"));
		   		 ps.setInt(12, input.getInt("cvv"));
		   		 ps.execute();
			 }  else if (!cardAddress.equals(input.getString("address_line1"))) {
				 
				 sql = "UPDATE payment SET address_line1 = ? WHERE user_id = ? AND card_number = ?";
				 PreparedStatement ps = conn.prepareStatement(sql);
				 ps.setString(1, input.getString("address_line1"));
				 ps.setString(2, userID);
				 ps.setString(3, input.getString("card_number"));
				 System.out.println(sql);
				 int rowsUpdated = ps.executeUpdate();
				 if (rowsUpdated > 0) {
				     System.out.println("An existing user was updated successfully!");
				 }
			 }  else {
				 return;
			 }
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setPaymentInfo: " + e.getMessage());
		}
	}
	
	public String getPaymentInfo(String userId) {
		
		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> getPaymentInfo");
		}
		String str = null;
		try {
			String sql = "SELECT * FROM payment WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				str = rs.getString("card_number");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> getPaymentInfo " + e.getMessage());
		}
		return str;
	}

	public String getStatus(String orderID) {
		if (conn == null) {
			return null;
		}
		String status = "";
		try {
			String sql = "SELECT status FROM orders WHERE order_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, orderID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				status = rs.getString("status");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

}