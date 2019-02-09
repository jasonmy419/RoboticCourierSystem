package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Address;
import entity.Address.AddressBuilder;
import entity.Address.GeoResponseType;
import entity.Address.State;

public class GeocodingAPI {

	private static final String METHOD = "/geocode";

	public Address geocoding(Address address) {

		if(address == null) {
			return null;
		}
		
		String addr = parseAddress(address);

		String query = String.format("address=%s&key=%s", addr, GoogleMapAPIUtil.API_KEY);
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

			System.out.println(obj.toString(4));

			if (obj.get("status").equals("OK") && !obj.isNull("results")) {
				JSONArray results = obj.getJSONArray("results");
				if(!results.isNull(0)) {
					JSONObject result = results.getJSONObject(0);
					if(!result.isNull("formatted_address")) {
						System.out.println(result.get("formatted_address"));
					}
					switch(address.getResponseType()){
					case PLACE_ID:
						return responseWithID(result);
					case COORDINATE:
						return responseWithCoordinate(result);
					default:
						System.out.println("in default");
						break;
					}	
				}	
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Address responseWithID(JSONObject object) throws JSONException {
		if(!object.isNull("place_id")) {
			
			Address addr = new AddressBuilder()
					.setPlaceID(object.getString("place_id"))
					.setResponseType(GeoResponseType.PLACE_ID)
					.build();
			
			return addr;
		}
		return null;
	}
	
	private Address responseWithCoordinate(JSONObject object) throws JSONException {
		if(!object.isNull("geometry")) {
			
			JSONObject geometry = object.getJSONObject("geometry");
			if(!geometry.isNull("location")) {
				JSONObject location = geometry.getJSONObject("location");
				Address addr = new AddressBuilder()
						.setLatitude(location.getDouble("lat"))
						.setLongitide(location.getDouble("lng"))
						.setResponseType(GeoResponseType.COORDINATE)
						.build();
				return addr;
			}
		}
		return null;
	}
	
	private String parseAddress(Address address) {
		// String template = "1600+Amphitheatre+Parkway,+Mountain+View,+CA";
		StringBuilder sb = new StringBuilder();
		sb.append(address.getStreetNum());
		
		String[] street = address.getStreetName().split(" ");
		
		for(String s: street) {
			sb.append('+');
			sb.append(s);
		}
		sb.append(',');
		
		String[] city = address.getCity().split(" ");
		
		for(String s: city) {
			sb.append('+');
			sb.append(s);
		}
		
		sb.append(',');
		sb.append('+');
		
		sb.append(address.getState().name());
		return sb.toString();
	}
	
	public static void main(String args[]) {
		GeocodingAPI api = new GeocodingAPI();
		Address ad = new AddressBuilder()
				.setStreetNum("717")
				.setStreetName("EastGate Ave")
				.setCity("St. Louis")
				.setState(State.MO)
				.setResponseType(GeoResponseType.PLACE_ID)
				.build();
		
		Address temp = api.geocoding(ad);
		System.out.println(temp.getPlaceID() 
				+ temp.getLatitude()
				+ temp.getLongitude()
				+ temp.getResponseType().name());
		
	}
}
