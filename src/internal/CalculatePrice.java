package internal;

import org.json.JSONObject;

import entity.TravelMode;
import external.CalculateFlightDistance;
import external.DirectionsAPI;
import rpc.RpcHelper;

public class CalculatePrice {

	public static double getPrice(int time, int distance, TravelMode mode) {
		double price = 0;
		switch(mode) {
		case FLYING: // this case is used on flying robots
			price += time * 1.0 / 300 + 2 * distance * 1.0 / 1609.344;
			break;
		case WALKING: // this case is used for walking robots
			price += time * 1.0 / 1200 + 5 * distance * 1.0 / 1609.344;
			break;
		default:
			System.out.println("Unable to calculate price!");
		}
		return price;	
	}
}
