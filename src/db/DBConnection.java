package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Address;
import entity.Order;
import entity.User;

public class DBConnection {

	private Connection conn;
	private final static int INIT_COUPON = 0;

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

	// Check the ratio of available couriers to total couriers in particular station
	public double getCourierRatio(String station_id, String type) {
		System.out.println("Type in db: " + type);
		if (conn == null) {
			return 0;
		}
		Timestamp time = null;
		int totalCount = 0, availableCount = 0;
		try {
			String sql = "SELECT time FROM couriers WHERE station_id = ? AND type = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, station_id);
			stmt.setString(2, type);
			ResultSet rs = stmt.executeQuery();
			Timestamp current_time = new Timestamp((new Date()).getTime());
			while (rs.next()) {
				time = rs.getTimestamp("time");
				totalCount++;
				if (current_time.after(time)) {
					availableCount++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (totalCount == 0)
			return 0;
		return availableCount * 1.0 / totalCount;
	}

	public JSONObject getParticularStationByCourierID(String courierID) {
		if (conn == null) {
			return null;
		}
		JSONObject obj = new JSONObject();
		String stationID = "";
		try {
			String sql = "SELECT station_id FROM couriers WHERE courier_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, courierID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				stationID = rs.getString("station_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			String sql = "SELECT station_lat, station_lon, street_number, street_name, city FROM stations WHERE station_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, stationID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				obj.put("station_lat", rs.getDouble("station_lat"));
				obj.put("station_lon", rs.getDouble("station_lon"));
				obj.put("street_number", rs.getString("street_number"));
				obj.put("street_name", rs.getString("street_name"));
				obj.put("city", rs.getString("city"));
			}

		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public String getParticularStationByCoordinate(double lat, double lon) {
		if (conn == null) {
			return null;
		}
		String s = "";
		try {
			String sql = "SELECT station_id FROM stations WHERE station_lat = ? AND station_lon = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				s = rs.getString("station_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public String getParticularStationByPlace(String street_num, String street_name, String city) {
		if (conn == null) {
			return null;
		}
		String s = "";
		try {
			// System.out.println("num is "+ street_num+", name is "+street_name+", city is
			// " + city);
			String sql = "SELECT station_id FROM stations WHERE street_number = ? AND street_name = ? AND city = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, street_num);
			stmt.setString(2, street_name);
			stmt.setString(3, city);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				s = rs.getString("station_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public String getAvailableCourier(String station, String type) {
		if (conn == null) {
			return null;
		}

		try {
			String sql = "SELECT courier_id, time FROM couriers WHERE station_id = ? AND type = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, station);
			stmt.setString(2, type);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String courierId = rs.getString("courier_id");
				Timestamp time = rs.getTimestamp("time");
				Timestamp curTime = new Timestamp((new Date()).getTime());
				if (curTime.after(time)) {
					return courierId;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getStation() {
		if (conn == null) {
			return null;
		}
		List<String> stationList = new ArrayList<>();
		try {
			String sql = "SELECT DISTINCT station_id FROM stations";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				stationList.add(rs.getString("station_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stationList;
	}

	// Get the latest coupon from user
	public double getCoupon(String userId) {
		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> getCoupon");
			return -1;
		}
		int couponNum = 0;
		try {
			String sql = "SELECT coupon FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				couponNum = rs.getInt("coupon");
				System.out.println("HHH Coupon is " + couponNum);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setCoupon: " + e.getMessage());
		}
		System.out.println("Coupon is " + couponNum);
		return couponNum * 1.0 / 100;
	}

	public String setCoupon(String userId, int coupon) {

		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setCoupon");
			return "DBConnection Error";
		}

		try {
			String sql = "UPDATE users SET coupon = ? WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, coupon);
			stmt.setString(2, userId);
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("An existing user " + userId + ", was updated successfully!");
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setCoupon: " + e.getMessage());
		}
		return "failed";
	}

	public void setCourierTime(Timestamp courierTime, String courierId) {
		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setCourierTime");
			return;
		}
		try {
			String sql = "UPDATE couriers SET time = ? WHERE courier_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, courierTime);
			stmt.setString(2, courierId);
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("An existing courier: " + courierId + ", was updated successfully!");
			}
			return;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setCourierTime: " + e.getMessage());
		}
	}

	public String setAddress(String streetNumber, String streetName, String city, String userId) {

		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setAddress");
			return null;
		}

		try {
			String sql = "SELECT address_id FROM address WHERE user_id = ? AND street_num = ? AND street_name= ? "
					+ "AND city = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, streetNumber);
			stmt.setString(3, streetName);
			stmt.setString(4, city);

			ResultSet rs = stmt.executeQuery();
			String addressId = null;
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			while (rs.next()) {
				addressId = rs.getString("address_id");
				obj.put("address_id", addressId);
				arr.put(obj);
				System.out.println(obj);
			}

			if (addressId != null) {
				return addressId;
			} else {
				sql = "INSERT IGNORE INTO address VALUES(?, ?, ?, ?, ?, ?)";
				stmt = conn.prepareStatement(sql);
				addressId = UUID.randomUUID().toString();
				stmt.setString(1, addressId);
				stmt.setString(2, userId);
				stmt.setString(3, streetNumber);
				stmt.setString(4, streetName);
				stmt.setString(5, city);
				stmt.setString(6, "CA");
				stmt.execute();
				return addressId;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setAddress: " + e.getMessage());
		}

		return null;
	}

	public void setReservation(Order ord) {

		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setReservation");
			return;
		}

		try {

			String sql = "INSERT IGNORE INTO orders VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ord.getOrderId());
			ps.setString(2, ord.getUserId());
			ps.setString(3, ord.getCourierId());
			ps.setString(4, ord.getItemId());
			ps.setString(5, ord.getType());
			ps.setString(6, ord.getStartAddressId());
			ps.setString(7, ord.getEndAddressId());
			ps.setTimestamp(8, ord.getTimestamp());
			ps.setDouble(9, ord.getRouteDuration());
			ps.setDouble(10, ord.getRouteDistance());
			ps.setDouble(11, ord.getRoutePrice());
			ps.setString(12, ord.getRoutePath());
			ps.setBoolean(13, ord.isComplete());
			ps.setBoolean(14, ord.isRecommended());
			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setReservation: " + e.getMessage());
		}
	}

	public String findOrderNumber(String UserId) {

		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> findOrderNumber");
			return null;
		}
		try {
			String sql = "SELECT order_id, end_time FROM orders WHERE user_id = ? AND COMPLETE = FALSE";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, UserId);
			ResultSet rs = stmt.executeQuery();
			String orderId = null;
			Timestamp st = null;
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			while (rs.next()) {
				orderId = rs.getString("order_id");
				st = rs.getTimestamp("end_time");
				obj.put("unfinished_order", orderId);
				arr.put(obj);
				System.out.println("end_time"+st.toString());
			}

			if (orderId != null) {
				sql = "UPDATE orders SET complete = ?, end_time = ? WHERE user_id = ? AND order_id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setBoolean(1, true);
				stmt.setTimestamp(2, st);
				stmt.setString(3, UserId);
				stmt.setString(4, orderId);
				stmt.executeUpdate();
				System.out.println(st.toString());
			}

			return orderId;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> findOrderNumber: " + e.getMessage());
		}
		return null;

	}

	public String setPaymentInfo(JSONObject input) {

		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> setPaymentInfo");
			return "DB Connection Failed";
		}

		try {
			String userId = input.getString("user_id");
			String cardNumber = input.getString("card_number");
			String cvv = input.getString("cvv");
			
			if (cardNumber.length() == 16) {
				for (int i = 0; i < cardNumber.length(); i++) {
					
					if (cardNumber.charAt(i) >= '0' && cardNumber.charAt(i) <= '9') {
						continue;
					}  else {
						return "Wrong Card Number";
					}
				}
			}  else {
				return "Wrong Card Number Digits";
			}
			
			if (cvv.length() == 3) {
				for (int i = 0; i < cvv.length(); i++) {
					
					if (cvv.charAt(i) >= '0' && cvv.charAt(i) <= '9') {
						continue;
					}  else {
						return "Wrong CVV Number";
					}
				}
			} else {
				return "Wrong CVV Number Digits";
			}

			String sql = "SELECT address_line1, zipcode FROM payment WHERE user_id = ? AND card_number = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, cardNumber);
			ResultSet rs = stmt.executeQuery();

			String cardAddress = null;
			int zipcode = -1;
			// JSONObject obj = new JSONObject();
			while (rs.next()) {
				cardAddress = rs.getString("address_line1");
				zipcode = rs.getInt("zipcode");
			}

			if (cardAddress == null && zipcode == -1) {
				sql = "INSERT IGNORE INTO payment VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, input.getString("last_name"));
				ps.setString(3, input.getString("first_name"));
				ps.setString(4, cardNumber);
				ps.setString(5, input.getString("address_line1"));
				ps.setString(6, null);
				ps.setString(7, input.getString("city"));
				ps.setInt(8, input.getInt("zipcode"));
				ps.setString(9, input.getString("state"));
				ps.setInt(10, input.getInt("month"));
				ps.setInt(11, input.getInt("year"));
				ps.setString(12, cvv);
				ps.execute();

				return null;
				
			} else if (!cardAddress.equals(input.getString("address_line1")) || zipcode != input.getInt("zipcode")) {

				sql = "UPDATE payment SET address_line1 = ?, zipcode = ?, address_line2 = ? , city = ?, state = ? WHERE user_id = ? AND card_number = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, input.getString("address_line1"));
				ps.setInt(2, input.getInt("zipcode"));
				ps.setString(3, input.getString("address_line2"));
				ps.setString(4, input.getString("city"));
				ps.setString(5, input.getString("state"));
				ps.setString(6, userId);
				ps.setString(7, cardNumber);
				int rowsUpdated = ps.executeUpdate();
				if (rowsUpdated > 0) {
					System.out.println("An existing user was updated successfully!");
				}
				return null;
			} else if (cardAddress.equals(input.getString("address_line1")) && zipcode == input.getInt("zipcode")) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> setPaymentInfo: " + e.getMessage());
		}
		return "Error";
	}

	public boolean getPaymentInfo(String userId) {

		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> getPaymentInfo");
		}

		JSONArray array = new JSONArray();
		try {
			JSONObject obj = new JSONObject();
			String sql = "SELECT card_number FROM payment WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				obj.put("card_number", rs.getString("card_number"));
				obj.put("user_id", userId);
				array.put(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error from src/db/DBConnection -> getPaymentInfo " + e.getMessage());
		}

		return array.length() > 0;
	}

	public String getStatus(String orderID) {
		if (conn == null) {
			System.err.println("DB connection failed from src/db/DBConnection -> getStatus");
			return null;
		}
		Timestamp cur = new Timestamp((new Date()).getTime());
		Timestamp status = null;
		try {
			String sql = "SELECT end_time FROM orders WHERE order_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, orderID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				status = rs.getTimestamp("end_time");
			}
			
			if (status.getTime() <= cur.getTime()) {
				StringBuilder sb = new StringBuilder();
				sb.append(status.getTime());
				return sb.toString();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "IN TRANSIT";
	}

	public JSONArray getStationAddress() {
		if (conn == null) {
			return null;
		}
		JSONArray array = new JSONArray();
		try {
			String sql = "SELECT station_id, street_number, street_name, city FROM stations";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("station_id", rs.getString("station_id"));
				obj.put("street_number", rs.getString("street_number"));
				obj.put("street_name", rs.getString("street_name"));
				obj.put("city", rs.getString("city"));
//				System.out.println(obj);
				array.put(obj);
			}

		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}
		return array;
	}

	public boolean signup(User user, Address addr) {
		if(user == null || addr == null) {
			return false;
		}
		
		if(createUser(user) && createAddress(addr, user)) {
			return true;
		}
		
		return false;
	}

	public String getFullname(String userId) {
		// TODO Auto-generated method stub
		if (conn == null) {
			return "";
		}
		String name = "";

		// Set<String> categories = new HashSet<>();
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

	public JSONObject getUserProfile (String userId) {
		if (conn == null) {
			return null;
		}
		JSONObject obj = new JSONObject();
		try {
			String sql = "SELECT first_name, last_name, coupon FROM users WHERE user_id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				obj.put("first_name", rs.getString("first_name"));
				obj.put("last_name", rs.getString("last_name"));
				obj.put("coupon", rs.getInt("coupon"));
			}
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONArray getUserOrders (String userId) {
		if (conn == null) {
			return null;
		}
		
		JSONArray array = new JSONArray();
		try {
			String sql = "SELECT * FROM orders WHERE user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("order_id", rs.getString("order_id"));
				obj.put("start_address", getAddressByAddressId(rs.getString("start_address_id")));
				obj.put("end_address", getAddressByAddressId(rs.getString("end_address_id")));
				obj.put("type", rs.getString("type"));
				obj.put("end_time", getStatus(rs.getString("order_id")));
				array.put(obj);
			}
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}
		return array;
	}

	public String getAddressByAddressId (String addressId) {
		if (conn == null) {
			System.out.println("DBConnection Error from getAddressByAddressId");
			return null;
		}

		try {
			String sql = "SELECT * FROM address WHERE address_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, addressId);
			ResultSet rs = statement.executeQuery();
			StringBuilder sb = new StringBuilder();

			while (rs.next()) {
				sb.append(rs.getString("street_num")).append(' ')
				.append(rs.getString("street_name")).append(' ')
				.append(rs.getString("city")).append(' ')
				.append(rs.getString("state")).append(' ');
			}

			return sb.toString();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error from getAddressByAddressId" + e.getMessage());
		}
	
		return null;
	}



	private boolean createUser(User user) {
		if (conn == null) {
			return false;
		}
		
		String sql = "INSERT IGNORE INTO users (user_id, password, first_name, last_name, address_id, zipcode, coupon) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement statement;

		try {
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, user.getUserId());
			statement.setString(2, user.getPasword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getAddress());
			statement.setString(6, user.getZipCode());
			statement.setInt(7, INIT_COUPON);
			int rs = statement.executeUpdate();
			System.out.println("insert users result: " + rs);
			if (rs == 1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("createUser: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private boolean createAddress(Address addr, User user) {
		if (conn == null) {
			return false;
		}
		
		String sql = "INSERT IGNORE INTO address (address_id, user_id, street_num, street_name, city, state) VALUES (?,?,?,?,?,?)";
		PreparedStatement statement;

		try {
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, addr.getAddressId());
			statement.setString(2, user.getUserId());
			statement.setString(3, addr.getStreetNum());
			statement.setString(4, addr.getStreetName());
			statement.setString(5, addr.getCity());
			statement.setString(6, addr.getState().name());
			int rs = statement.executeUpdate();
			System.out.println("insert address result: " + rs);
			if (rs == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
