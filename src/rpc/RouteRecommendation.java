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
import entity.State;
import entity.TravelMode;
import external.DirectionsAPI;
import entity.Address.AddressBuilder;
import entity.Address.InputType;
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
			
			System.out.println(input.toString(4));
			
			
			
			Address origin, waypoint, destination;
			
			if(!input.isNull("origin")) {
				origin = new AddressBuilder().parseJson(input.getJSONObject("origin"));
			}else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! Cannot find origin address"));
				return;
			}
			
			if(!input.isNull("waypoint")) {
				waypoint = new AddressBuilder().parseJson(input.getJSONObject("waypoint"));
			}else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! Cannot find waypoint address"));
				return;
			}
			
			if(!input.isNull("destination")) {
				destination = new AddressBuilder().parseJson(input.getJSONObject("destination"));
			}else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! Cannot find destination address"));
				return;
			}
			
			System.out.println(origin.getStreetName() + origin.getStreetNum() + origin.getCity());
			
			// choose the transfer mode
			int typeCode = input.getInt("mode");
			List<Route> routes = new ArrayList<>();
			
			switch(typeCode) {
			case 1: // this case is used on flying robots
				break;
			case 2: // this case is used for walking robots
				DirectionsAPI api = new DirectionsAPI();
				routes = api.directions(origin, destination, waypoint);
				break;
			default:
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "Failed! invalid mode"));
				return;
			}
			
			System.out.println(routes.size());
			JSONArray res = new JSONArray();
			for(Route r: routes) {
				res.put(r.toJSONObject());
			}
			
			JSONObject result = new JSONObject();
			result.put("result", res);
			
			RpcHelper.writeJsonObject(response, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
