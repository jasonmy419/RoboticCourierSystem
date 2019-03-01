package external;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;

import entity.Address;
import entity.Route;
import entity.Route.RouteBuilder;
import internal.CalculatePrice;
import internal.CourierAvailabilityRatio;
import entity.TravelMode;
import entity.ItemSize;
import internal.GetAvailableCourier;
import internal.StationAddress;

public class CalculateFlightDistance implements GoogleMapAPI {

	private static final int FLYING_SPEED = 25; // 25m / s
	private static final double R = 6371.01; // Radius of the earth

	@Override
	public List<Route> getRouteInfo(Address origin, Address destination, Address waypoint, ItemSize size) {
		// TODO Auto-generated method stub
		List<Route> routes = new ArrayList<>();
		List<RouteBuilder> builders = calculateDistance(origin, destination, waypoint);
		for(RouteBuilder builder: builders) {
			getExtraRouteInfo(builder, origin, size);
			routes.add(builder.build());
		}
		return routes;
	}

	private List<RouteBuilder> calculateDistance(Address origin, Address destination, Address waypoint) {

		List<RouteBuilder> builders = new ArrayList<>();

		if (origin == null || destination == null || waypoint == null) {
			return builders;
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

		int[] first = getPolyline(ori, way);
		int[] second = getPolyline(way, des);

		JSONObject object = new JSONObject();
		EncodedPolyline fullPath = new EncodedPolyline(list);
		try {
			object.put("points", fullPath.getEncodedPath());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RouteBuilder builder = new RouteBuilder();
		builder.setDistance(first[0] + second[0]);
		builder.setDuration(first[1] + second[1]);
		builder.setTravelMode(TravelMode.FLYING);
		builder.setPolylineOverview(object);

		builders.add(builder);

		return builders;
	}

	private int[] getPolyline(LatLng l1, LatLng l2) {

		int[] poly = new int[2]; // 0th is distance, 1st is duration
		List<LatLng> list = new ArrayList<>();

		list.add(l1);
		list.add(l2);

		EncodedPolyline path = new EncodedPolyline(list);

		int distance = distance(l1, l2);

		poly[0] = distance;
		poly[1] = (int) (distance / FLYING_SPEED);

		JSONObject object = new JSONObject();
		try {
			object.put("points", path.getEncodedPath());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return poly;
	}

	public int returnTime(double start_lat, double start_lon, double end_lat, double end_lon) {
		LatLng ori = new LatLng(start_lat, start_lon);
		LatLng dest = new LatLng(end_lat, end_lon);
		int dist = distance(ori, dest);
		return (int) (dist / FLYING_SPEED);
	}

	private int distance(LatLng origin, LatLng destination) {

		double height = 0.0;

		double latDistance = Math.toRadians(destination.lat - origin.lat);
		double lonDistance = Math.toRadians(destination.lng - origin.lng);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(origin.lat))
				* Math.cos(Math.toRadians(destination.lat)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return (int) Math.sqrt(distance);
	}

	private void getExtraRouteInfo(RouteBuilder builder, Address origin, ItemSize size) {

		double price = CalculatePrice.getPrice(builder.getDuration(), builder.getDistance(), TravelMode.FLYING, size);
		String station_id = StationAddress.getStationAddressByPlaceID(origin.getStreetNum(), origin.getStreetName(),
				origin.getCity());
		price = CalculatePrice.priceFluctuation(price, station_id, TravelMode.FLYING);

		String courierID = GetAvailableCourier.getAvailableCourierByPlace(origin.getStreetNum(), origin.getStreetName(),
				origin.getCity(), "Air");
		double courier_ratio = CourierAvailabilityRatio.courierRatio(station_id, TravelMode.FLYING);

		builder.setPrice(price);
		builder.setCourier(courierID);
		builder.setCourierRatio(courier_ratio);
	}
}
