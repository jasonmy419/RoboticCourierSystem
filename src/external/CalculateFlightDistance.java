package external;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;

import entity.Address;
import entity.Polyline;
import entity.Polyline.PolylineBuilder;
import entity.Route;
import entity.Route.RouteBuilder;
import internal.CalculatePrice;
import entity.TravelMode;
import entity.ItemSize;
import internal.GetAvailableCourier;
import internal.StationAddress;

public class CalculateFlightDistance {
	
	private static final int FLYING_SPEED = 25; // 25m / s
	private static final double R = 6371.01; // Radius of the earth
	
	public List<Route> calculateDistance(Address origin, Address destination, Address waypoint, ItemSize size){
		
		List<Route> routes = new ArrayList<>();
		
		if (origin == null || destination == null || waypoint == null) {
			return routes;
		}

		GeocodingAPI api = new GeocodingAPI();
		api.geocoding(origin);
		api.geocoding(waypoint);
		api.geocoding(destination);
		
		List<LatLng> list = new ArrayList<>();
		
		LatLng ori = new LatLng(origin.getLatitude(), origin.getLongitude());
		LatLng des = new LatLng(destination.getLatitude(), destination.getLongitude());
		LatLng way = new LatLng(waypoint.getLatitude(), waypoint.getLongitude());
		
		list.add(ori);
		list.add(way);
		list.add(des);
		
		List<Polyline> polylines = new ArrayList<>();
		Polyline first = getPolyline(ori, way);
		Polyline second = getPolyline(way, des);
		
		polylines.add(first);
		polylines.add(second);
		
		JSONObject object = new JSONObject();
		EncodedPolyline fullPath = new EncodedPolyline(list);
		try {
			object.put("points", fullPath.getEncodedPath());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Set up route price
		double price = CalculatePrice.getPrice(first.getDuration() + second.getDuration(), 
				first.getDistance() + second.getDistance(), TravelMode.FLYING, size);
		String station_id = StationAddress.getStationAddressByPlaceID(origin.getStreetNum(), 
				origin.getStreetName(), origin.getCity());
		price = CalculatePrice.priceFluctuation(price, station_id, TravelMode.FLYING);
		
		String courierID = GetAvailableCourier.getAvailableCourierByPlace(origin.getStreetNum(), 
				origin.getStreetName(), origin.getCity(), "Air");
		
		RouteBuilder builder = new RouteBuilder();
		builder.setDistance(first.getDistance() + second.getDistance());
		builder.setDuration(first.getDuration() + second.getDuration());
		builder.setTravelMode(TravelMode.FLYING);
		builder.setRoute(polylines);
		builder.setPolylineOverview(object);
		builder.setPrice(price);
		builder.setCourier(courierID);
				
		routes.add(builder.build());
		
		return routes;
	}
	
	private Polyline getPolyline(LatLng l1, LatLng l2) {
		
		List<LatLng> list = new ArrayList<>();
		
		list.add(l1);
		list.add(l2);
		
		EncodedPolyline path = new EncodedPolyline(list);
		
		PolylineBuilder builder = new PolylineBuilder();
		
		int distance = distance(l1, l2);
		
		builder.setDistance(distance);
		builder.setDuration((int)(distance / FLYING_SPEED));
		
		JSONObject object = new JSONObject();
		try {
			object.put("points", path.getEncodedPath());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		builder.setPolyline(object);
		
		return builder.build();
		
	}
	
	public int returnTime(double start_lat, double start_lon, double end_lat, double end_lon) {
		LatLng ori = new LatLng(start_lat, start_lon);
		LatLng dest = new LatLng(end_lat, end_lon);
		int dist = distance(ori, dest);
		return (int)(dist / FLYING_SPEED);
	}
	
	private int distance(LatLng origin, LatLng destination) {
		
		double height = 0.0;

	    double latDistance = Math.toRadians(destination.lat - origin.lat);
	    double lonDistance = Math.toRadians(destination.lng - origin.lng);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(origin.lat)) * Math.cos(Math.toRadians(destination.lat))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return (int)Math.sqrt(distance);
	}
}
