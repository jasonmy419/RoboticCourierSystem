package internal;

import db.DBConnection;

public class GetAvailableCourier {

	public static String getAvailableCourierByCoordinate(double lat, double lon, String type) {
		DBConnection conn = new DBConnection();
		String s = "";
		String station = "";
		try {
			station = conn.getParticularStationByCoordinate(lat, lon);
			s = conn.getAvailableCourier(station, type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return s;
	}
	
	public static String getAvailableCourierByPlace(String street_num, String street_name, String city, String type) {
		DBConnection conn = new DBConnection();
		String s = "";
		String station = "";
		try {
			station = conn.getParticularStationByPlace(street_num, street_name, city);
			s = conn.getAvailableCourier(station, type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return s;
	}
}
