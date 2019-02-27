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
import entity.Address.AddressBuilder;
import entity.Address.GeoResponseType;
import entity.Address.InputType;
import entity.Polyline;
import entity.Polyline.PolylineBuilder;
import entity.Route;
import entity.Route.RouteBuilder;
import internal.CalculatePrice;
import internal.GetAvailableCourier;
import internal.StationAddress;
import entity.State;
import entity.TravelMode;
import entity.ItemSize;

public class DirectionsAPI {

	private static final String METHOD = "/directions";
	private static final boolean ALTERNATIVES = true;
	private static final String TRAVEL_MODE = "walking";

	public List<Route> directions(Address origin, Address destination, Address waypoint, ItemSize size) {

		List<Route> routes = new ArrayList<>();

		if (origin == null || destination == null || waypoint == null) {
			return routes;
		}

		GeocodingAPI api = new GeocodingAPI();
		api.geocoding(origin);
		api.geocoding(waypoint);
		api.geocoding(destination);
		
//		System.out.println("origin is here: "+waypoint.getStreetName());

		String query = String.format("%s&mode=%s&alternatives=%s&key=%s&avoid=tolls|highways|ferries",
				this.generateQuery(origin, waypoint, destination),
				TRAVEL_MODE,
				ALTERNATIVES,
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

			// System.out.println(obj.toString(4));

			if (obj.get("status").equals("OK") && !obj.isNull("routes")) {
				JSONArray array = obj.getJSONArray("routes");
				System.out.println("Route numbers: " + array.length());
				for (int i = 0; i < array.length(); i++) {
					Route tmp = routeParse(array.getJSONObject(i), size, origin);
					routes.add(tmp);
					System.out.println("s in main directionAPI is " + routes.get(0).getCourierID());
					System.out.println("s in main directionAPI tmp is " + tmp.getCourierID());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routes;
	}
	
	public int returnDuration(Address origin, Address destination) {

		GeocodingAPI api = new GeocodingAPI();
		api.geocoding(origin);
		api.geocoding(destination);
		

		String query = String.format("%s&mode=%s&alternatives=%s&key=%s&avoid=tolls|highways|ferries",
				this.generateTwoQuery(origin, destination),
				TRAVEL_MODE,
				ALTERNATIVES,
				GoogleMapAPIUtil.API_KEY);
		String url = GoogleMapAPIUtil.PREFIX + METHOD + GoogleMapAPIUtil.OUTPUT_FORMAT + "?" + query;
		int duration = Integer.MAX_VALUE;
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

			// System.out.println(obj.toString(4));

			if (obj.get("status").equals("OK") && !obj.isNull("routes")) {
				JSONArray array = obj.getJSONArray("routes");
				System.out.println("Route numbers: " + array.length());
				for (int i = 0; i < array.length(); i++) {
					Route tmp = routeParse(array.getJSONObject(i), ItemSize.SMALL, origin);
//					routes.add(tmp);
					duration = Math.min(duration, tmp.getDuration());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return duration;
	}

	private Route routeParse(JSONObject object, ItemSize size, Address origin) throws JSONException, UnsupportedEncodingException {

		RouteBuilder routeBuilder = new RouteBuilder();

		if (!object.isNull("legs")) {
			JSONArray legs = object.getJSONArray("legs");
			JSONObject leg = legs.getJSONObject(0);

			if (!leg.isNull("duration")) {
				JSONObject duration = leg.getJSONObject("duration");
				routeBuilder.setDuration(duration.getInt("value"));
			}

			if (!leg.isNull("distance")) {
				JSONObject distance = leg.getJSONObject("distance");
				routeBuilder.setDistance(distance.getInt("value"));
			}

			if (!leg.isNull("steps")) {
				JSONArray steps = leg.getJSONArray("steps");
				List<Polyline> Polylines = new ArrayList<>();

				for (int i = 0; i < steps.length(); i++) {
					JSONObject step = steps.getJSONObject(i);
					PolylineBuilder polyBuilder = new PolylineBuilder();

					if (!step.isNull("duration")) {
						JSONObject duration = step.getJSONObject("duration");
						polyBuilder.setDuration(duration.getInt("value"));
					}

					if (!step.isNull("distance")) {
						JSONObject distance = step.getJSONObject("distance");
						polyBuilder.setDistance(distance.getInt("value"));
					}

					if (!step.isNull("polyline")) {
						polyBuilder.setPolyline(step.getJSONObject("polyline"));

					}

					Polyline poly = polyBuilder.build();
					Polylines.add(poly);
				}
				routeBuilder.setRoute(Polylines);
				routeBuilder.setTravelMode(TravelMode.WALKING);
			}
			
			// Set up route price
			double price = CalculatePrice.getPrice(leg.getJSONObject("duration").getInt("value"), 
					leg.getJSONObject("distance").getInt("value"), TravelMode.WALKING, size);
			String station_id = StationAddress.getStationAddressByPlaceID(origin.getStreetNum(), 
					origin.getStreetName(), origin.getCity());
			price = CalculatePrice.priceFluctuation(price, station_id, TravelMode.WALKING);
			
			routeBuilder.setPrice(price);
			

		}

		if (!object.isNull("overview_polyline")) {
			routeBuilder.setPolylineOverview(object.getJSONObject("overview_polyline"));
		}
		
			String courierID = GetAvailableCourier.getAvailableCourierByPlace(origin.getStreetNum(),
					origin.getStreetName(), origin.getCity(), "Robot");
//			System.out.println("s in directionAPI is " + courierID);
			routeBuilder.setCourier(courierID);

		return routeBuilder.build();
	}
	
	private String generateTwoQuery(Address origin, Address destination) {
		// all 3 input should follow same format
		String prefix = null;
		String query = null;
		
		switch(origin.getResponseType()) {
		case PLACE_ID:
			prefix = "origin=place_id:%s&destination=place_id:%s";
			query = String.format(prefix, origin.getPlaceID(), destination.getPlaceID());
			break;
		case COORDINATE:
			prefix = "origin=%s&destination=%s";
			query = String.format(prefix, origin.getCoordinate(), destination.getCoordinate());
			break;
		}
		return query;
	}

	
	private String generateQuery(Address origin, Address waypoint, Address destination) {
		// all 3 input should follow same format
		String prefix = null;
		String query = null;
		
		switch(origin.getResponseType()) {
		case PLACE_ID:
			prefix = "origin=place_id:%s&destination=place_id:%s&waypoints=place_id:%s";
			query = String.format(prefix, origin.getPlaceID(), destination.getPlaceID(), waypoint.getPlaceID());
			break;
		case COORDINATE:
			prefix = "origin=%s&destination=%s&waypoints=%s";
			query = String.format(prefix, origin.getCoordinate(), destination.getCoordinate(), waypoint.getCoordinate());
			break;
		}
		return query;
	}

//	public static void main(String args[]) throws JSONException {
//		DirectionsAPI api = new DirectionsAPI();
//
//		// 68 Willow Road, Menlo Park, CA
//		Address origin = new AddressBuilder().setStreetNum("68").setStreetName("Willow Road").setCity("Menlo Park")
//				.setState(State.CA).setInputType(InputType.ADDRESS_STRING).setResponseType(GeoResponseType.COORDINATE)
//				.build();
//
//		// 383 University Ave, Palo Alto, CA
//		Address wayPoint = new AddressBuilder().setStreetNum("383").setStreetName("University Ave").setCity("Palo Alto")
//				.setState(State.CA).setInputType(InputType.ADDRESS_STRING).setResponseType(GeoResponseType.COORDINATE)
//				.build();
//
//		// 1929 Menalto Ave, Menlo Park, CA
//		Address destination = new AddressBuilder().setStreetNum("1929").setStreetName("Menalto Ave")
//				.setCity("Menlo Park").setState(State.CA).setInputType(InputType.ADDRESS_STRING)
//				.setResponseType(GeoResponseType.COORDINATE).build();
//
//		List<Route> routes = api.directions(origin, destination, wayPoint, ItemSize.SMALL);
//		for (Route r : routes) {
//			System.out.println(r.getPolyline());
//		}
//	}
}
