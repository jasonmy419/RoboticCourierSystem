package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Polyline {
	
    private int distance;
    private int duration;
    private JSONObject polyline;

	private Polyline(PolylineBuilder builder) { 
        this.distance = builder.distance;
        this.duration = builder.duration;
        this.polyline = builder.polyline;
	}

	public int getDistance() {
		return distance;
	}

	public int getDuration() {
		return duration;
	}
	
	public JSONObject getPolyline() {
		return polyline;
	}
	
	public JSONObject toJSONObject() {
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("duration", duration);
			obj.put("distance", distance);
			obj.put("polyline", polyline);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static class PolylineBuilder {
		
    	private int duration;
    	private int distance;
    	private JSONObject polyline;
    	
    	public PolylineBuilder setDuration(int duration) {
    		this.duration = duration;
    		return this;
    	}
    	
    	public PolylineBuilder setDistance(int distance) {
    		this.distance = distance;
    		return this;
    	}
    	
    	public PolylineBuilder setPolyline(JSONObject polyline) {
    		this.polyline = polyline;
    		return this;
    	}
    	 
    	public Polyline build() {
			return new Polyline(this);
		}
    }
}
