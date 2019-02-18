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

public class DBConnection {

	private Connection conn;	

	public DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(DBUtility.URL);

		} catch (Exception e) {
			e.printStackTrace();
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

	// TODO
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