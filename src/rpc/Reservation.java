package rpc;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DBConnection;
import entity.Order;
/**
 * Servlet implementation class PlaceOrder
 */
@WebServlet("/orders")
public class Reservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DecimalFormat df = new DecimalFormat(".##");
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
			double duration = detail.getDouble("duration");
			double distance = detail.getDouble("distance");
			String type = detail.getString("mode").equals("FLYING") ? "D" : "R";
			double price = detail.getDouble("price");
			price = Double.valueOf(df.format(price));
			
			String status = "TRANSIT";
			JSONObject obj = (JSONObject) detail.get("overview_polyline");
			String routePath = obj.getString("points");
			String itemId = UUID.randomUUID().toString();
			String orderId = UUID.randomUUID().toString();
			boolean complete = false;
			
			
			
			JSONArray arr = (JSONArray)input.get("waypoint");
			String startStreeNumber = arr.get(0).toString();
			String startStreeName = arr.get(1).toString();
			String startCity = arr.get(1).toString();
			
			arr = (JSONArray)input.get("destination");
			String endStreeNumber = arr.get(0).toString();
			String endStreeName = arr.get(1).toString();
			String endCity = arr.get(2).toString();
			
			String courierId = input.getString("couier_id");
			String userId = input.getString("user_id");
			
			Order ord = new Order.OrderBuilder()
					 .userId(userId)
					 .orderId(orderId)
					 .courierId(courierId)
					 .itemId(itemId)
					 .status(status)
					 .type(type)
					 .startStreeNumber(startStreeNumber)
					 .startStreeName(startStreeName)
					 .startCity(startCity)
					 .endStreeNumber(endStreeNumber)
					 .endStreeName(endStreeName)
					 .endCity(endCity)
					 .routeDuration(duration)
					 .routeDistance(distance)
					 .routePrice(price)
					 .routePath(routePath)
					 .complete(complete)
					 .build();
	
			System.out.println(ord.toString());
			
			
			RpcHelper.writeJsonObject(response, new JSONObject().put("reservation status:", "success"));
		}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in rpc/orders/POST -> " + e.getMessage());
		} finally {
			conn.close();
		}
		
	}

}
