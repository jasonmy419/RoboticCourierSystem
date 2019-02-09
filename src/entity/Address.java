package entity;

public class Address {
	
	private String streetNum;
	private String streetName;
	private String city;
	private State state;
	
	private double latitude;
	private double longitude;
	
	private String placeID;
	
	private GeoResponseType type;
	
	private Address(AddressBuilder builder) {
		
		this.streetNum = builder.streetNum;
		this.streetName = builder.streetName;
		this.city = builder.city;
		this.state = builder.state;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.placeID = builder.placeID;
		this.type = builder.type == null ? GeoResponseType.PLACE_ID : builder.type;
		
	}
	
	public String getStreetNum() {
		return this.streetNum;
	}
	
	public String getStreetName() {
		return this.streetName;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public State getState() {
		return this.state;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public String getPlaceID() {
		return this.placeID;
	}
	
	public GeoResponseType getResponseType() {
		return this.type;
	}
	
	public static class AddressBuilder {

		private String streetNum;
		private String streetName;
		private String city;
		private State state;
		
		private double latitude;
		private double longitude;
		
		private String placeID;
		
		private GeoResponseType type;
		
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
		
		public AddressBuilder setResponseType(GeoResponseType type) {
			this.type = type;
			return this;
		}
		
		public Address build() {
			return new Address(this);
		}
	}
	
	
	public static enum GeoResponseType {
		PLACE_ID,
		COORDINATE
	}
	
	public static enum State {
		CA,
		MO
	}

}
