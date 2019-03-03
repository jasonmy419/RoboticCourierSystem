package rpc;

import java.io.IOException;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DBConnection;
import entity.Order;
import util.Tool;
/**
 * Servlet implementation class PlaceOrder
 */
@WebServlet("/orders")
public class Reservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private static DecimalFormat df = new DecimalFormat(".##");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reservation() {
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
		
		DBConnection conn = new DBConnection();
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
			
			JSONObject detail = (JSONObject) input.get("detail");
			double returnTime = detail.getDouble("courier_return_time");
			double distance = detail.getDouble("distance");
			double duration = detail.getDouble("duration");
			String type = detail.getString("mode").equals("FLYING") ? "D" : "R";
			String courierId = detail.getString("courier");
			double price = detail.getDouble("price");
			boolean isRecommended = detail.getBoolean("isRecommended");
			
			Timestamp courierTime = new Timestamp((new Date()).getTime() 
												  + (long)duration*1000 
												  + (long)returnTime*1000);
			
			conn.setCourierTime(courierTime, courierId);
			
			Timestamp end = new Timestamp((new Date()).getTime() + (long)duration*1000);
			JSONObject obj = (JSONObject) detail.get("overview_polyline");
			
			String routePath = Tool.StringProccessing(obj.getString("points"));
			String itemId = UUID.randomUUID().toString();
			String orderId = UUID.randomUUID().toString();
			boolean complete = false;
			String userId = input.getString("user_id");
			
			JSONArray arr = (JSONArray)input.get("waypoint");
			String startStreeNumber = arr.get(0).toString();
			String startStreeName = arr.get(1).toString();
			String startCity = arr.get(2).toString();
			String startAddressId = conn.setAddress(startStreeNumber, startStreeName, startCity, userId);
			
			
			arr = (JSONArray)input.get("destination");
			String endStreeNumber = arr.get(0).toString();
			String endStreeName = arr.get(1).toString();
			String endCity = arr.get(2).toString();
			String endAddressId = conn.setAddress(endStreeNumber, endStreeName, endCity, userId);
			
			Order ord = new Order.OrderBuilder()
					 .userId(userId)
					 .orderId(orderId)
					 .courierId(courierId)
					 .itemId(itemId)
					 .endTime(end)
					 .type(type)
					 .startAddressId(startAddressId)
					 .endAddressId(endAddressId)
					 .routeDuration(duration)
					 .routeDistance(distance)
					 .routePrice(price)
					 .routePath(routePath)
					 .complete(complete)
					 .isRecommended(isRecommended)
					 .build();
	
			System.out.println(ord.toString());
			
			conn.setReservation(ord);
			RpcHelper.writeJsonObject(response, new JSONObject().put("reservation status:", "success"));
		}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in rpc/orders/POST -> " + e.getMessage());
		} finally {
			conn.close();
		}
		
	}

}
