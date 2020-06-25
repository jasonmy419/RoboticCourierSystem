package internal;

import db.DBConnection;
import entity.TravelMode;

public class CourierAvailabilityRatio {

	// Ratio of particular type courier in one station
	public static double courierRatio(String station_id, TravelMode mode) {
		DBConnection conn = new DBConnection();
		double ratio = 0;
		try {
			ratio = conn.getCourierRatio(station_id, (mode == TravelMode.FLYING) ? "Air" : "Robot");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ratio;
	}
}
