package internal;

import java.text.DecimalFormat;

import db.DBConnection;
import entity.TravelMode;
import entity.ItemSize;

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
	
	// Add some factors which can make price fluctuation
	public static double priceFluctuation(double price, String station_id, TravelMode mode) {
//		System.out.println("Original price is " + price);
		double ratio = CourierAvailabilityRatio.courierRatio(station_id, mode);
		if (ratio < 0.1) {
			price *= 1.2;
		}
		else if (ratio < 0.2) {
			price *= 1.1;
		}
//		System.out.println("Current price is " + price);
		
		DecimalFormat df = new DecimalFormat("#.00");
	    String priceStr = df.format(price);
//	    System.out.println("price is "+ priceStr); 
	    price = Double.parseDouble(priceStr);
		return price;
	}
	
	// Help get coupon from db and reset the user's coupon
	public static double getCoupon(String userId) {
		DBConnection conn = new DBConnection();
		double coupon = 0;
		try {
			coupon = conn.getCoupon(userId);
			conn.setCoupon(userId, 0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return coupon;
	}
}
