package rpc;

import java.io.IOException;
import java.text.DecimalFormat;
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

import entity.Address;
import entity.Route;
import entity.Route.RouteBuilder;
import entity.TravelMode;
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
			Address waypoint, destination;
			Address waypoint_place, destination_place;
			ItemSize size;
			String userid;
			
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
			
			// Parse item size and userid
			size = ItemSize.valueOf(input.getString("size"));
			userid = input.getString("userid");
			
			// Get user's latest coupon ratio
			double coupon_ratio = CalculatePrice.getCoupon(userid);
			
			// choose the transfer mode
			List<Route> buffer_routes = new ArrayList<>();	
			
			waypoint = new AddressBuilder().parseJson(input.getJSONObject("waypoint"), 1);
			destination = new AddressBuilder().parseJson(input.getJSONObject("destination"), 1);
			for (int i = 0; i < originArray.length / 2; i++) {
				CalculateFlightDistance distanceAPI = new CalculateFlightDistance();
				List<Route> retList = distanceAPI.getRouteInfo(originArray[i], destination, waypoint, size);
				buffer_routes.addAll(retList);
			}
			
			waypoint_place = new AddressBuilder().parseJson(input.getJSONObject("waypoint"), 2);
			destination_place = new AddressBuilder().parseJson(input.getJSONObject("destination"), 2);
			for (int i = originArray.length / 2; i < originArray.length; i++) {
				DirectionsAPI directionsAPI = new DirectionsAPI();
				List<Route> newList = directionsAPI.getRouteInfo(originArray[i], destination_place, waypoint_place, size);
				buffer_routes.addAll(newList);
			}
			
			List<Route> routes = new ArrayList<>();	
			for (Route r : buffer_routes) {
				if (r.getCourierID() != null) {
					routes.add(r);
					System.out.println("ID is" + r.getCourierID());
				}
			}
			
			for (Route r : routes) {
				System.out.println("IDDDD is" + r.getCourierID());
			}
			// Show error warning when no routes recommended!
			if (routes.size() == 0) {
//				JSONObject errorWarning = new JSONObject();
//				errorWarning.put("Error: ", "No routes recommended!");
				JSONArray returnArr = new JSONArray();
				RpcHelper.writeJsonArray(response, returnArr);
				return;
			}
			System.out.println("The size of routes is "+ routes.size());
			
			for (int i = 0; i < routes.size();i++) {
				double price = CalculatePrice.getPrice(routes.get(i).getDuration(), 
						routes.get(i).getDistance(), routes.get(i).getMode(), size);
				System.out.println("Price should be "+ price + ", but it is now " + routes.get(i).getPrice());
			}
			
			JSONArray res = new JSONArray();
			
			JSONObject waypointInfo = new JSONObject();
			waypointInfo.put("way_point_lat", waypoint.getLatitude());
			waypointInfo.put("way_point_lon", waypoint.getLongitude());
			waypointInfo.put("street_number", waypoint_place.getStreetNum());
			waypointInfo.put("street_name", waypoint_place.getStreetName());
			waypointInfo.put("city", waypoint_place.getCity());
			
			JSONObject destPointInfo = new JSONObject();
			destPointInfo.put("destination_point_lat", destination.getLatitude());
			destPointInfo.put("destination_point_lon", destination.getLongitude());
			destPointInfo.put("street_number", destination_place.getStreetNum());
			destPointInfo.put("street_name", destination_place.getStreetName());
			destPointInfo.put("city", destination_place.getCity());
			
			DecimalFormat df = new DecimalFormat("#.00");
//		    String priceStr = df.format(price);
////		    System.out.println("price is "+ priceStr); 
//		    price = Double.parseDouble(priceStr);
		    
			// Sort the route based on duration
			Collections.sort(routes, new Comparator<Route>() {
				@Override
				public int compare(Route r1, Route r2) {
					return (r1.getDuration() < r2.getDuration()) ? -1 : 1;
				}
			});
			
			JSONObject fast_station = StationAddress.getStationAddress(routes.get(0).getCourierID());
			// double fast_ratio = routes.get(0).getCourierRatio();
			double fast_oldPrice = routes.get(0).getPrice();
			double fast_newPrice = fast_oldPrice * (1 - coupon_ratio);
			String fast_tmp = df.format(fast_newPrice);
			fast_newPrice = Double.parseDouble(fast_tmp);

			RouteBuilder fastestRoute = new RouteBuilder();
			fastestRoute.setDistance(routes.get(0).getDistance());
			fastestRoute.setDuration(routes.get(0).getDuration());
			fastestRoute.setPolylineOverview(routes.get(0).getPolyline());
			fastestRoute.setTravelMode(routes.get(0).getMode());
			fastestRoute.setPrice(routes.get(0).getPrice());
			fastestRoute.setPrice(fast_newPrice);
			fastestRoute.setCourier(routes.get(0).getCourierID());
			fastestRoute.setCourierRatio(routes.get(0).getCourierRatio());
			
			JSONObject fastRoute = fastestRoute.build().toJSONObject();
			
			fastRoute.put("size", size);
			fastRoute.put("way_point", waypointInfo);
			fastRoute.put("destination_point", destPointInfo);
			fastRoute.put("station_point", fast_station);
			fastRoute.put("oldPrice", fast_oldPrice);
			
			int fast_duration = calculateReturnTime(routes.get(0), fast_station, destination, destination_place);
			fastRoute.put("courier_return_time", fast_duration);
			
			
			
			// Sort the route based on distance
			Collections.sort(routes, new Comparator<Route>() {
				@Override
				public int compare(Route r1, Route r2) {
					return (r1.getPrice() < r2.getPrice()) ? -1 : 1;
				}
			});
			
			JSONObject cheap_station = StationAddress.getStationAddress(routes.get(0).getCourierID());
			double cheap_ratio = routes.get(0).getCourierRatio();			
			double cheap_oldPrice = routes.get(0).getPrice();
			double cheap_newPrice = cheap_oldPrice * (1 - coupon_ratio);
			String cheap_tmp = df.format(cheap_newPrice);
			cheap_newPrice = Double.parseDouble(cheap_tmp);
			
			RouteBuilder cheapestRoute = new RouteBuilder();
			cheapestRoute.setDistance(routes.get(0).getDistance());
			cheapestRoute.setDuration(routes.get(0).getDuration());
			cheapestRoute.setTravelMode(routes.get(0).getMode());
			cheapestRoute.setPolylineOverview(routes.get(0).getPolyline());
			cheapestRoute.setPrice(cheap_newPrice);
			cheapestRoute.setCourier(routes.get(0).getCourierID());
			cheapestRoute.setCourierRatio(routes.get(0).getCourierRatio());
			
			JSONObject cheapRoute = cheapestRoute.build().toJSONObject();
			
			cheapRoute.put("size", size);
			cheapRoute.put("way_point", waypointInfo);
			cheapRoute.put("destination_point", destPointInfo);
			cheapRoute.put("station_point", cheap_station);
			cheapRoute.put("oldPrice", cheap_oldPrice);
			
			int cheap_duration = calculateReturnTime(routes.get(0), cheap_station, destination, destination_place);
			cheapRoute.put("courier_return_time", cheap_duration);
			
//			for (int i = 0; i < routes.size(); i++) {
//				System.out.println("Price is" + routes.get(i).getCourierRatio());
//			}
			// Recommend route
			JSONObject recommend_route = null;
			for (int i = 0; i < routes.size(); i++) {
//				System.out.println("Routes ratio is RRR " + routes.get(i).getCourierRatio() + "," + routes.get(i).getPrice());
				if (routes.get(i).getDuration() == fast_duration) break;
				if (routes.get(i).getCourierRatio() <= cheap_ratio) continue;
				JSONObject recommend_station = StationAddress.getStationAddress(routes.get(i).getCourierID());
				double recommend_oldPrice = routes.get(i).getPrice();
				double recommend_newPrice = recommend_oldPrice * (1 - coupon_ratio);
				String rec_tmp = df.format(recommend_newPrice);
				recommend_newPrice = Double.parseDouble(rec_tmp);
				
				RouteBuilder recommendRoute = new RouteBuilder();
				recommendRoute.setDistance(routes.get(i).getDistance());
				recommendRoute.setDuration(routes.get(i).getDuration());
				recommendRoute.setTravelMode(routes.get(i).getMode());
				recommendRoute.setPolylineOverview(routes.get(i).getPolyline());
				recommendRoute.setPrice(recommend_newPrice);
				recommendRoute.setCourier(routes.get(i).getCourierID());
				recommendRoute.setCourierRatio(routes.get(i).getCourierRatio());
				
				recommend_route = recommendRoute.build().toJSONObject();
				
				recommend_route.put("size", size);
				recommend_route.put("way_point", waypointInfo);
				recommend_route.put("destination_point", destPointInfo);
				recommend_route.put("station_point", recommend_station);
				recommend_route.put("oldPrice", recommend_oldPrice);
				
				int recommend_duration = calculateReturnTime(routes.get(i), recommend_station, destination, destination_place);
				recommend_route.put("courier_return_time", recommend_duration);
				recommend_route.put("isRecommended", true);
				break;
			}
			
			fastRoute.put("isRecommended", (recommend_route == null) ? true : false);
			res.put(fastRoute);
			cheapRoute.put("isRecommended", false);
			res.put(cheapRoute);
			if (recommend_route != null) {
				res.put(recommend_route);
			}
			
			System.out.println(res.length());
			RpcHelper.writeJsonArray(response, res);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	// The time spent when courier returns from destination to corresponding station
	private int calculateReturnTime(Route route, JSONObject station, Address destination, Address destination_place) throws JSONException {
		int duration = 0;
		if (route.getMode() == TravelMode.FLYING) {
			CalculateFlightDistance distanceAPI = new CalculateFlightDistance();
			duration = distanceAPI.returnTime(destination.getLatitude(), destination.getLongitude(), 
					station.getDouble("station_lat"), station.getDouble("station_lon"));
		}
		else if (route.getMode() == TravelMode.WALKING) {
			Address station_place = new AddressBuilder().parseJson(station, 2);
			DirectionsAPI directionsAPI = new DirectionsAPI();
			duration = directionsAPI.getReturnDuration(destination_place, station_place);
		}
		return duration;
	}
}
