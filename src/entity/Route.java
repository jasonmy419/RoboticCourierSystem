package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Route {
	
	private int duration;
	private int distance;
	private TravelMode mode;
	private JSONObject polyline;
	private double price;
	private String courierID;
	private double ratio;
	
	private Route (RouteBuilder builder) {
		this.duration = builder.duration;
		this.distance = builder.distance;
		this.mode = builder.mode;
		this.polyline = builder.polyline;
		this.price = builder.price;
		this.courierID = builder.courierID;
		this.ratio = builder.ratio;
	}
	
	public double getCourierRatio() {
		return this.ratio;
	}
	
	public String getCourierID() {
		return this.courierID;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public TravelMode getMode() {
		return this.mode;
	}
	
	public JSONObject getPolyline() {
		return this.polyline;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("duration", duration);
			obj.put("distance", distance);
			obj.put("mode", mode.name());
			obj.put("overview_polyline", polyline);
			obj.put("price", price);
			obj.put("courier", courierID);
			obj.put("courier_ratio", ratio);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static class RouteBuilder {
		
		private int duration;
		private int distance;
		private TravelMode mode;
		private JSONObject polyline;
		private double price;
		private String courierID;
		private double ratio;
		
		public int getDistance() {
			return this.distance;
		}
		
		public int getDuration() {
			return this.duration;
		}
		
		public RouteBuilder setCourierRatio(double ratio) {
			this.ratio = ratio;
			return this;
		}
		
		public RouteBuilder setCourier(String courierID) {
			this.courierID = courierID;
			return this;
		}
		
		public RouteBuilder setPrice(double price) {
			this.price = price;
			return this;
		}
		
		public RouteBuilder setDuration(int time) {
			this.duration = time;
			return this;
		}
		
		public RouteBuilder setDistance(int distance) {
			this.distance = distance;
			return this;
		}
		
		public RouteBuilder setTravelMode(TravelMode mode) {
			this.mode = mode;
			return this;
		}
		
		public RouteBuilder setPolylineOverview(JSONObject polyline) {
			this.polyline = polyline;
			return this;
		}
		
		public Route build() {
			return new Route(this);
		}
	}
}
