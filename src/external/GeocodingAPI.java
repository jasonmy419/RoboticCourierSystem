package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Address;
import entity.Address.GeoResponseType;

public class GeocodingAPI {

	private static final String METHOD = "/geocode";

	public void geocoding(Address address) {

		if(address == null) {
			return;
		}
		
		System.out.println(address.getGeoQuery());
		String url = GoogleMapAPIUtil.PREFIX + METHOD + GoogleMapAPIUtil.OUTPUT_FORMAT + "?" + address.getGeoQuery();

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

			if (obj.get("status").equals("OK") && !obj.isNull("results")) {
				JSONArray results = obj.getJSONArray("results");
				if(!results.isNull(0)) {
					JSONObject result = results.getJSONObject(0);
					if(!result.isNull("formatted_address")) {
						System.out.println("formatted_address: " + result.get("formatted_address"));
					}
					
					switch(address.getResponseType()){
					case PLACE_ID:
						responseWithID(result, address);
						break;
					case COORDINATE:
						responseWithCoordinate(result, address);
						break;
					default:
						System.out.println("Cannot find right response type");
						break;
					}	
				}	
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void responseWithID(JSONObject object, Address address) throws JSONException {
		if(!object.isNull("place_id")) {
			address.setPlaceID(object.getString("place_id"));
			address.setResponseType(GeoResponseType.PLACE_ID);
		}
	}
	
	private void responseWithCoordinate(JSONObject object, Address address) throws JSONException {
		if(!object.isNull("geometry")) {
			JSONObject geometry = object.getJSONObject("geometry");
			if(!geometry.isNull("location")) {
				JSONObject location = geometry.getJSONObject("location");
				address.setLatitude(location.getDouble("lat"));
				address.setLongitude(location.getDouble("lng"));
				address.setResponseType(GeoResponseType.COORDINATE);
			}
		}
	}
}
