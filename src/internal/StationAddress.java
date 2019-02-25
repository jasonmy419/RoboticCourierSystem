package internal;

import db.DBConnection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Address;
import external.CalculateFlightDistance;
import external.DirectionsAPI;
import rpc.RpcHelper;
import entity.Address.AddressBuilder;
import entity.Route;


public class StationAddress {

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

}
