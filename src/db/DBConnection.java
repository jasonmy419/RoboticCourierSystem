package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public void setReservation(String userId, String routeId, JSONObject input) {
		
		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setReservation");
			return;
		}
		try {
			
			 String sql = "INSERT IGNORE INTO reservations VALUES (?, ?, ?, ?, ?, ?, ?)";
	   		 PreparedStatement ps = conn.prepareStatement(sql);
	   		 ps.setString(1, userId);
	   		 ps.setString(2, routeId);
	   		 ps.setString(3, input.getString("type"));
	   		 ps.setDouble(4, input.getDouble("route_duration"));
	   		 ps.setDouble(5, input.getDouble("route_distance"));
	   		 ps.setDouble(6, input.getDouble("route_price"));
	   		 ps.setString(7, input.getString("route_path"));
	   		 ps.execute();
			
		}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setReservation: " + e.getMessage());
		}
	}
	
	public boolean setPaymentInfo(String userId, JSONObject input) {
	
		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setPaymentInfo");
			return false;
		}

		try {
			
			 String sql = "SELECT address_line1, zipcode FROM payment WHERE user_id = ? AND card_number = ? ";
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 stmt.setString(1,userId);
			 stmt.setString(2,input.getString("card_number"));
			 ResultSet rs = stmt.executeQuery();
			 
			 String cardAddress = null;
			 int zipcode = -1;
			 
			 while (rs.next()) {
				 cardAddress = rs.getString("address_line1");
				 zipcode = rs.getInt("zipcode");
			 }
			 
			 if (cardAddress == null || cardAddress.length() == 0 && zipcode == -1) {
				 sql = "INSERT IGNORE INTO payment VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		   		 PreparedStatement ps = conn.prepareStatement(sql);
		   		 ps.setString(1, userId);
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
		   		 
		   		 return true;
			 }  else if (!cardAddress.equals(input.getString("address_line1")) || zipcode != input.getInt("zipcode")) {
				 
				 sql = "UPDATE payment SET address_line1 = ?, zipcode = ?, address_line2 = ? , city = ?, state = ? WHERE user_id = ? AND card_number = ?";
				 PreparedStatement ps = conn.prepareStatement(sql);
				 ps.setString(1, input.getString("address_line1"));
				 ps.setInt(2, input.getInt("zipcode"));
				 ps.setString(3, input.getString("address_line2"));
				 ps.setString(4, input.getString("city"));
				 ps.setString(5, input.getString("state"));
				 ps.setString(6, userId);
				 ps.setString(7, input.getString("card_number"));
				 int rowsUpdated = ps.executeUpdate();
				 if (rowsUpdated > 0) {
				     System.out.println("An existing user was updated successfully!");
				 }
				 return true;
			 }  else {
				 return true;
			 }
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setPaymentInfo: " + e.getMessage());
		}
		return false;
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
	
	public JSONArray getStationAddress() {
		if (conn == null) {
			return null;
		}
		JSONArray array =  new JSONArray();
		try {
			String sql = "SELECT street_number, street_name, city FROM stations";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("street_number", rs.getString("street_number"));
				obj.put("street_name", rs.getString("street_name"));
				obj.put("city", rs.getString("city"));
				System.out.println(obj);
				array.put(obj);
			}
			
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}
		return array;
	}

	public boolean signup(String userId, String password, String firstname, String lastname) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "INSERT INTO users (user_id, password, first_name, last_name) VALUES (?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, password);
			statement.setString(3, firstname);
			statement.setString(4, lastname);
			int rs = statement.executeUpdate();
			if (rs == 1) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}

		return false; // Dummy
	}

	public String getFullname(String userId) {
		// TODO Auto-generated method stub
		if (conn == null) {
			return "";
		}
		String name = "";

		Set<String> categories = new HashSet<>();
		try {
			String sql = "SELECT first_name, last_name FROM users WHERE user_id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				name = rs.getString("first_name") + " " + rs.getString("last_name");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return name;
	}

	public boolean verifyLogin(String userId, String password) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "SELECT user_id FROM users WHERE user_id = ? AND password = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;

	}
	
	
}