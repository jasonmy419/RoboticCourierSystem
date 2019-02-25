package internal;

import java.text.DecimalFormat;

import org.json.JSONObject;

import entity.TravelMode;
import entity.ItemSize;
import external.CalculateFlightDistance;
import external.DirectionsAPI;
import rpc.RpcHelper;

public class CalculatePrice {

	public static double getPrice(int time, int distance, TravelMode mode, ItemSize size) {
		double price = 0;
		int priceOfSize = (size == ItemSize.SMALL) ? 2 : ((size == ItemSize.MEDIUM) ? 5 : 8);
		
		switch(mode) {
		case FLYING: // this case is used on flying robots
			price += time * 1.0 / 300 + 5 * distance * 1.0 / 1609.344 + priceOfSize;
			break;
		case WALKING: // this case is used for walking robots
			price += time * 1.0 / 1200 + 2 * distance * 1.0 / 1609.344 + priceOfSize;
			break;
		default:
			System.out.println("Unable to calculate price!");
		}
		
		DecimalFormat df = new DecimalFormat("#.00");
	    String priceStr = df.format(price);
//	    System.out.println("price is "+ priceStr); 
	    double newPrice = Double.parseDouble(priceStr);
	    
		return newPrice;	
	}
}
