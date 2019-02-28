package internal;

import db.DBConnection;

import org.json.JSONArray;
import org.json.JSONObject;


public class StationAddress {

	public static JSONObject getParticularStationByCourierID(String courierID) {
		DBConnection conn = new DBConnection();
		JSONObject address = null;
		try {
			address = conn.getParticularStationByCourierID(courierID);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return address;
	}
	
	public JSONArray getAddress() {
		DBConnection conn = new DBConnection();
		JSONArray address = null;
		try {
			address = conn.getStationAddress();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return address;
	}
	
	public static JSONObject getStationAddress(String courierID) {
		DBConnection conn = new DBConnection();
		JSONObject obj = null;
		try {
			obj = conn.getParticularStationByCourierID(courierID);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return obj;
	}
	
	public static String getStationAddressByPlaceID(String street_num, String street_name, String city) {
		DBConnection conn = new DBConnection();
		String obj = null;
		try {
			obj = conn.getParticularStationByPlace(street_num, street_name, city);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return obj;
	}

}
