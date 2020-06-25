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
import entity.Address.InputType;

public class GeocodingAPI {

	private static final String METHOD = "/geocode";

	public String[] reverseGeocoding(double lat, double lon) {
		String[] response = new String[3];

		Address address = new AddressBuilder().setLatitude(lat).setLongitide(lon).setInputType(InputType.COORDINATE)
				.setResponseType(GeoResponseType.ADDRESS_STRING).build();
		
		geocoding(address);
		
		response[0] = address.getStreetNum();
		response[1] = address.getStreetName();
		response[2] = address.getCity();
		
		return response;
	}

	public void geocoding(Address address) {

		if (address == null) {
			return;
		}

		String url = GoogleMapAPIUtil.PREFIX + METHOD + GoogleMapAPIUtil.OUTPUT_FORMAT + "?" + getGeoQuery(address);

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
				if (!results.isNull(0)) {
					JSONObject result = results.getJSONObject(0);
					if (!result.isNull("formatted_address")) {
						System.out.println("formatted_address: " + result.get("formatted_address"));
					}

					switch (address.getResponseType()) {
					case PLACE_ID:
						responseWithID(result, address);
						break;
					case COORDINATE:
						responseWithCoordinate(result, address);
						break;
					case ADDRESS_STRING:
						responseWithString(result, address);
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
		if (!object.isNull("place_id")) {
			address.setPlaceID(object.getString("place_id"));
		}
	}

	private void responseWithCoordinate(JSONObject object, Address address) throws JSONException {
		if (!object.isNull("geometry")) {
			JSONObject geometry = object.getJSONObject("geometry");
			if (!geometry.isNull("location")) {
				JSONObject location = geometry.getJSONObject("location");
				address.setLatitude(location.getDouble("lat"));
				address.setLongitude(location.getDouble("lng"));
			}
		}
	}

	private void responseWithString(JSONObject object, Address address) throws JSONException {
		if(!object.isNull("address_components")) {
			JSONArray components = object.getJSONArray("address_components");
			for(int i = 0; i < components.length(); i++) {
				JSONObject component = components.getJSONObject(i);
				if(!component.isNull("types")) {
					JSONArray types = component.getJSONArray("types");
					for(int j = 0; j < types.length(); j++) {
						if(types.getString(j).equals("street_number")) {
							address.setStreetNum(component.getString("long_name"));
							break;
						}
						if(types.getString(j).equals("route")) {
							address.setStreetName(component.getString("long_name"));
							break;
						}
						if(types.getString(j).equals("administrative_area_level_2")) {
							address.setCity(component.getString("long_name"));
							break;
						}
					}
				}
			}
		}
	}

	private String getGeoQuery(Address origin) {
		String prefix = null;
		String query = null;
		switch (origin.getInputType()) {
		case ADDRESS_STRING:
			prefix = "address=%s&key=%s";
			query = String.format(prefix, origin.getParsedString(), GoogleMapAPIUtil.API_KEY);
			break;
		case PLACE_ID:
			break;
		case COORDINATE:
			prefix = "latlng=%s&key=%s";
			query = String.format(prefix, origin.getCoordinate(), GoogleMapAPIUtil.API_KEY);
			break;
		}

		return query;
	}
}
