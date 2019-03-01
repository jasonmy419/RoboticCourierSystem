package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Address;
import entity.Route;
import entity.Route.RouteBuilder;
import internal.CalculatePrice;
import internal.CourierAvailabilityRatio;
import internal.GetAvailableCourier;
import internal.StationAddress;
import entity.TravelMode;
import entity.ItemSize;

public class DirectionsAPI implements GoogleMapAPI {

	private static final String METHOD = "/directions";
	private static final boolean ALTERNATIVES = true;
	private static final String TRAVEL_MODE = "walking";

	public List<Route> getRouteInfo(Address origin, Address destination, Address waypoint, ItemSize size) {
		List<Route> routes = new ArrayList<>();
		List<RouteBuilder> builders = directions(origin, destination, waypoint, true);

		for (RouteBuilder builder : builders) {
			getExtraRouteInfo(builder, origin, size);
			routes.add(builder.build());
		}
		return routes;
	}

	public int getReturnDuration(Address origin, Address destination) {
		int duration = Integer.MAX_VALUE;
		List<RouteBuilder> builders = directions(origin, destination, null, false);
		for (RouteBuilder builder : builders) {
			duration = Math.min(duration, builder.getDuration());
		}
		return duration;
	}

	private List<RouteBuilder> directions(Address origin, Address destination, Address waypoint, boolean enable) {
		// enable is a boolean to decide whether use waypoint in this api
		List<RouteBuilder> routes = new ArrayList<>();

		if (origin == null || destination == null || (enable && waypoint == null)) {
			return routes;
		}

		GeocodingAPI api = new GeocodingAPI();
		api.geocoding(origin);
		api.geocoding(destination);

		if (enable)
			api.geocoding(waypoint);

		String query = String.format("%s&mode=%s&alternatives=%s&key=%s&avoid=tolls|highways|ferries",
				this.generateQuery(origin, waypoint, destination, enable), TRAVEL_MODE, ALTERNATIVES,
				GoogleMapAPIUtil.API_KEY);
		String url = GoogleMapAPIUtil.PREFIX + METHOD + GoogleMapAPIUtil.OUTPUT_FORMAT + "?" + query;

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();
			System.out.println("Sending request to url: " + url);
			System.out.println("Response code: " + responseCode);

			if (responseCode != 200) {
				throw new Exception("responseCode: " + responseCode);
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			JSONObject obj = new JSONObject(response.toString());

//			System.out.println(obj.toString(4));

			if (obj.get("status").equals("OK") && !obj.isNull("routes")) {
				JSONArray array = obj.getJSONArray("routes");
				System.out.println("Route numbers: " + array.length());
				for (int i = 0; i < array.length(); i++) {
					RouteBuilder builder = routeParse(array.getJSONObject(i));
					routes.add(builder);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routes;
	}

	private RouteBuilder routeParse(JSONObject object) throws JSONException, UnsupportedEncodingException {

		RouteBuilder routeBuilder = new RouteBuilder();
		int distanceSum = 0;
		int durationSum = 0;

		if (!object.isNull("legs")) {
			JSONArray legs = object.getJSONArray("legs");
			for (int i = 0; i < legs.length(); i++) {
				JSONObject leg = legs.getJSONObject(i);

				if (!leg.isNull("duration")) {
					JSONObject duration = leg.getJSONObject("duration");
					durationSum += duration.getInt("value");
				}

				if (!leg.isNull("distance")) {
					JSONObject distance = leg.getJSONObject("distance");
					distanceSum += distance.getInt("value");
				}
			}
		}

		routeBuilder.setDuration(durationSum);
		routeBuilder.setDistance(distanceSum);
		routeBuilder.setTravelMode(TravelMode.WALKING);

		if (!object.isNull("overview_polyline")) {
			routeBuilder.setPolylineOverview(object.getJSONObject("overview_polyline"));
		}

		return routeBuilder;
	}

	private String generateQuery(Address origin, Address waypoint, Address destination, boolean enable) {
		// all 3 input should follow same format
		String prefix = null;
		String query = null;

		switch (origin.getResponseType()) {
		case PLACE_ID:
			if (enable) {
				prefix = "origin=place_id:%s&destination=place_id:%s&waypoints=place_id:%s";
				query = String.format(prefix, origin.getPlaceID(), destination.getPlaceID(), waypoint.getPlaceID());
			} else {
				prefix = "origin=place_id:%s&destination=place_id:%s";
				query = String.format(prefix, origin.getPlaceID(), destination.getPlaceID());
			}
			break;
		case COORDINATE:
			if (enable) {
				prefix = "origin=%s&destination=%s&waypoints=%s";
				query = String.format(prefix, origin.getCoordinate(), destination.getCoordinate(),
						waypoint.getCoordinate());
			} else {
				prefix = "origin=%s&destination=%s";
				query = String.format(prefix, origin.getCoordinate(), destination.getCoordinate());
			}
			break;
		default:
			break;
		}
		return query;
	}

	private void getExtraRouteInfo(RouteBuilder builder, Address origin, ItemSize size) {
		double price = CalculatePrice.getPrice(builder.getDuration(), builder.getDistance(), TravelMode.WALKING, size);
		String station_id = StationAddress.getStationAddressByPlaceID(origin.getStreetNum(), origin.getStreetName(),
				origin.getCity());
		price = CalculatePrice.priceFluctuation(price, station_id, TravelMode.WALKING);
		builder.setPrice(price);

		double courier_ratio = CourierAvailabilityRatio.courierRatio(station_id, TravelMode.FLYING);
		builder.setCourierRatio(courier_ratio);

		String courierID = GetAvailableCourier.getAvailableCourierByPlace(origin.getStreetNum(), origin.getStreetName(),
				origin.getCity(), "Robot");
		builder.setCourier(courierID);
	}
}
