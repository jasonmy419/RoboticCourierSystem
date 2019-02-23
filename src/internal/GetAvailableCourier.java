package internal;

import java.util.ArrayList;
import java.util.List;


import db.DBConnection;

public class GetAvailableCourier {

	public List<String> getAvailableCourier() {
		DBConnection conn = new DBConnection();
		List<String> list = new ArrayList<>();
		List<String> stationList;
		try {
			stationList = conn.getStation();
			for (String station : stationList) {
				list.add(conn.getAvailableCourier(station, "Robot"));
				list.add(conn.getAvailableCourier(station, "Air"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		for (String s : list) {
			System.out.println(s);
		}
		return list;
	}
}
