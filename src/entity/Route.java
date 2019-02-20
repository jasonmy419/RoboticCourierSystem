package entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Route {
	
	private int duration;
	private int distance;
	private TravelMode mode;
	private List<Polyline> route;
	private JSONObject polyline;
	private double price;
	
	private Route (RouteBuilder builder) {
		this.duration = builder.duration;
		this.distance = builder.distance;
		this.mode = builder.mode;
		this.route = builder.route;
		this.polyline = builder.polyline;
		this.price = builder.price;
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
	
	public List<Polyline> getRoute(){
		return this.route;
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
			obj.put("route", this.toJSONArray());
			obj.put("overview_polyline", polyline);
			obj.put("price", price);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	private JSONArray toJSONArray() {
		JSONArray array = new JSONArray();
		
		for(Polyline p: route) {
			array.put(p.toJSONObject());
		}
		
		return array;
	}
	
	public static class RouteBuilder {
		
		private int duration;
		private int distance;
		private TravelMode mode;
		private List<Polyline> route;
		private JSONObject polyline;
		private double price;
		
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
		
		public RouteBuilder setRoute(List<Polyline> list) {
			List<Polyline> route = new ArrayList<>();
			for(Polyline coor: list) {
				route.add(coor);
			}
			this.route = route;
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
