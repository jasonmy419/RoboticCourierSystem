package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Address;
import external.CalculateFlightDistance;
import external.DirectionsAPI;
import entity.Address.AddressBuilder;
import entity.Route;


/**
 * Servlet implementation class Route
 */
@WebServlet("/route")
public class RouteRecommendation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RouteRecommendation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		JSONObject input = RpcHelper.readJSONObject(request);
		
		if(input == null) {
			try {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! No valid input"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		try {
			Address origin, waypoint, destination;
			int typeCode = 0;
			
			if(!input.isNull("mode")) {
				typeCode = input.getInt("mode");
			}else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! Cannot find mode"));
				return;
			}
			
			if(!input.isNull("origin")) {
				origin = new AddressBuilder().parseJson(input.getJSONObject("origin"), typeCode);
			}else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! Cannot find origin address"));
				return;
			}
			
			if(!input.isNull("waypoint")) {
				waypoint = new AddressBuilder().parseJson(input.getJSONObject("waypoint"), typeCode);
			}else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! Cannot find waypoint address"));
				return;
			}
			
			if(!input.isNull("destination")) {
				destination = new AddressBuilder().parseJson(input.getJSONObject("destination"), typeCode);
			}else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! Cannot find destination address"));
				return;
			}
			
			System.out.println(origin.getStreetName() + origin.getStreetNum() + origin.getCity());
			
			// choose the transfer mode
			List<Route> routes = new ArrayList<>();
			
			switch(typeCode) {
			case 1: // this case is used on flying robots
				CalculateFlightDistance distanceAPI = new CalculateFlightDistance();
				routes = distanceAPI.calculateDistance(origin, destination, waypoint);
				break;
			case 2: // this case is used for walking robots
				DirectionsAPI directionsAPI = new DirectionsAPI();
				routes = directionsAPI.directions(origin, destination, waypoint);
				break;
			default:
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! no mode match"));
				return;
			}
			
			System.out.println(routes.size());
			
			JSONObject res = new JSONObject();
			res.put("result", toJSONArray(routes));
			
			RpcHelper.writeJsonObject(response, res);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	private JSONArray toJSONArray(List<Route> routes) {
		JSONArray array = new JSONArray();
		
		for(Route r: routes) {
			array.put(r.toJSONObject());
		}
		
		return array;
	}
}
