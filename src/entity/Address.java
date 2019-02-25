package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Address {

	private String addressId;
	private String streetNum;
	private String streetName;
	private String city;
	private State state;

	private double latitude;
	private double longitude;

	private String placeID;

	private InputType input;
	private GeoResponseType response;

	private Address(AddressBuilder builder) {

		this.addressId = builder.addressId;
		this.streetNum = builder.streetNum;
		this.streetName = builder.streetName;
		this.city = builder.city;
		this.state = builder.state;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.placeID = builder.placeID;
		this.input = builder.input;
		this.response = builder.response;
	}

	public String getAddressId() {
		return addressId;
	}
	
	public String getStreetNum() {
		return this.streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}
	
	public String getStreetName() {
		return this.streetName;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return this.state;
	}

	public void setLatitude(double lat) {
		this.latitude = lat;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLongitude(double lon) {
		this.longitude = lon;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setPlaceID(String id) {
		this.placeID = id;
	}

	public String getPlaceID() {
		return this.placeID;
	}

	public InputType getInputType() {
		return this.input;
	}

	public void setResponseType(GeoResponseType type) {
		this.response = type;
	}

	public GeoResponseType getResponseType() {
		return this.response;
	}

	public String getCoordinate() {
		// lat,lon
		StringBuilder sb = new StringBuilder();
		sb.append(this.getLatitude());
		sb.append(',');
		sb.append(this.getLongitude());

		return sb.toString();
	}

	public String getParsedString() {
		// String template = "1600+Amphitheatre+Parkway,+Mountain+View,+CA";
		StringBuilder sb = new StringBuilder();
		sb.append(this.getStreetNum());

		String[] street = this.getStreetName().split(" ");

		for (String s : street) {
			sb.append('+');
			sb.append(s);
		}
		sb.append(',');

		String[] city = this.getCity().split(" ");

		for (String s : city) {
			sb.append('+');
			sb.append(s);
		}

		sb.append(',');
		sb.append('+');

		sb.append(this.getState().name());
		return sb.toString();
	}

	public static class AddressBuilder {

		private String addressId;
		private String streetNum;
		private String streetName;
		private String city;
		private State state;

		private double latitude;
		private double longitude;

		private String placeID;

		private InputType input;
		private GeoResponseType response;

		public AddressBuilder setAddressId(String addressId) {
			this.addressId= addressId;
			return this;
		}
		
		public AddressBuilder setStreetNum(String streetNum) {
			this.streetNum = streetNum;
			return this;
		}

		public AddressBuilder setStreetName(String streetName) {
			this.streetName = streetName;
			return this;
		}

		public AddressBuilder setCity(String city) {
			this.city = city;
			return this;
		}

		public AddressBuilder setState(State state) {
			this.state = state;
			return this;
		}

		public AddressBuilder setLatitude(double lat) {
			this.latitude = lat;
			return this;
		}

		public AddressBuilder setLongitide(double lon) {
			this.longitude = lon;
			return this;
		}

		public AddressBuilder setPlaceID(String place) {
			this.placeID = place;
			return this;
		}

		public AddressBuilder setInputType(InputType type) {
			this.input = type;
			return this;
		}

		public AddressBuilder setResponseType(GeoResponseType type) {
			this.response = type;
			return this;
		}

		public Address build() {
			return new Address(this);
		}

		public Address parseJson(JSONObject object, int input) {

			AddressBuilder address = new AddressBuilder();
			try {
				address.setStreetNum(object.getString("street_number")).setStreetName(object.getString("street_name"))
						.setCity(object.getString("city")).setState(State.CA).setInputType(InputType.ADDRESS_STRING);

				switch (input) {
				case 1:
					address.setResponseType(GeoResponseType.COORDINATE);
					break;
				case 2:
					address.setResponseType(GeoResponseType.PLACE_ID);
					break;
				default:
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return address.build();
		}
	}

	// this is the origin input type from front end
	public enum InputType {
		ADDRESS_STRING, PLACE_ID, COORDINATE
	}

	// front end - InputType -> (string) --> geocodingapi - GeoResponseType ->
	// coordinate
	// this is the required input type in logistic
	public enum GeoResponseType {
		PLACE_ID, COORDINATE, ADDRESS_STRING
	}
}
