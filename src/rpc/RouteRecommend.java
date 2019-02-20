package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import entity.Address;
import entity.Route;
import entity.Route.RouteBuilder;
import entity.Address.AddressBuilder;
import entity.ItemSize;
import external.CalculateFlightDistance;
import external.DirectionsAPI;
import internal.StationAddress;
import internal.CalculatePrice;

/**
 * Servlet implementation class RouteRecommend
 */
@WebServlet("/routeRecommend")
public class RouteRecommend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RouteRecommend() {
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
			ItemSize size;
			
			StationAddress address = new StationAddress();
			JSONArray array = address.getAddress();
			
			
			Address[] originArray = new Address[array.length() * 2];
			for (int i = 0; i < array.length() * 2; i++) {
				if (i < array.length()) {
					originArray[i] = new AddressBuilder().parseJson(array.getJSONObject(i), 1);
				}
				else {
					originArray[i] = new AddressBuilder().parseJson(array.getJSONObject(i - array.length()), 2);
				}
			}
			
			System.out.println(originArray[0].getStreetName() + originArray[0].getStreetNum() + originArray[0].getCity());
			
			// Parse item size
			size = ItemSize.valueOf(input.getString("size"));
			
			// choose the transfer mode
			List<Route> routes = new ArrayList<>();	
			
			waypoint = new AddressBuilder().parseJson(input.getJSONObject("waypoint"), 1);
			destination = new AddressBuilder().parseJson(input.getJSONObject("destination"), 1);
			for (int i = 0; i < originArray.length / 2; i++) {
				CalculateFlightDistance distanceAPI = new CalculateFlightDistance();
				List<Route> retList = distanceAPI.calculateDistance(originArray[i], destination, waypoint, size);
				routes.addAll(retList);
			}
			
			waypoint = new AddressBuilder().parseJson(input.getJSONObject("waypoint"), 2);
			destination = new AddressBuilder().parseJson(input.getJSONObject("destination"), 2);
			for (int i = originArray.length / 2; i < originArray.length; i++) {
				DirectionsAPI directionsAPI = new DirectionsAPI();
				List<Route> newList = directionsAPI.directions(originArray[i], destination, waypoint, size);
				routes.addAll(newList);
			}
			
			
			for (int i = 0; i < routes.size();i++) {
				double price = CalculatePrice.getPrice(routes.get(i).getDuration(), 
						routes.get(i).getDistance(), routes.get(i).getMode(), size);
				System.out.println("Price should be "+ price + ", but it is now " + routes.get(i).getPrice());
			}
			
			JSONArray res = new JSONArray();
			
			// Sort the route based on duration
			Collections.sort(routes, new Comparator<Route>() {
				@Override
				public int compare(Route r1, Route r2) {
					return (r1.getDuration() < r2.getDuration()) ? -1 : 1;
				}
			});
			
			RouteBuilder fastestRoute = new RouteBuilder();
			fastestRoute.setDistance(routes.get(0).getDistance());
			fastestRoute.setDuration(routes.get(0).getDuration());
			fastestRoute.setPolylineOverview(routes.get(0).getPolyline());
			fastestRoute.setTravelMode(routes.get(0).getMode());
			fastestRoute.setRoute(routes.get(0).getRoute());
			fastestRoute.setPrice(routes.get(0).getPrice());
			
			JSONObject fastRoute = fastestRoute.build().toJSONObject();
			fastRoute.put("size", size);
			
			res.put(fastRoute);
			
			// Sort the route based on distance
			Collections.sort(routes, new Comparator<Route>() {
				@Override
				public int compare(Route r1, Route r2) {
					return (r1.getPrice() < r2.getPrice()) ? -1 : 1;
				}
			});
			
			RouteBuilder cheapestRoute = new RouteBuilder();
			cheapestRoute.setDistance(routes.get(0).getDistance());
			cheapestRoute.setDuration(routes.get(0).getDuration());
			cheapestRoute.setRoute(routes.get(0).getRoute());
			cheapestRoute.setTravelMode(routes.get(0).getMode());
			cheapestRoute.setPolylineOverview(routes.get(0).getPolyline());
			cheapestRoute.setPrice(routes.get(0).getPrice());
			
			JSONObject cheapRoute = cheapestRoute.build().toJSONObject();
			cheapRoute.put("size", size);
			
			res.put(cheapRoute);
			
			
			System.out.println(res.length());
			RpcHelper.writeJsonArray(response, res);
			
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
