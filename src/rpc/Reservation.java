package rpc;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DBConnection;
/**
 * Servlet implementation class PlaceOrder
 */
@WebServlet("/orders")
public class Reservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
//			JSONObject obj = (JSONObject) input.get("0");
			
			JSONObject obj = (JSONObject)input.get("0");
			JSONArray arr = (JSONArray)input.get("waypoint");
			String str = arr.get(0).toString();
//			double price = obj.getDouble("duration"); 
			System.out.println("Number: "+ str);
		}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in rpc/orders/POST -> " + e.getMessage());
		}
		
//		DBConnection conn = new DBConnection();
//		UUID uuid = UUID.randomUUID();
//		try {
//			JSONObject input = RpcHelper.readJSONObject(request);
//			String userId = request.getParameter("user_id");
//			String routeId = uuid.toString();
//			
//			conn.setReservation(userId,routeId,input);
//			RpcHelper.writeJsonObject(response, new JSONObject().put("reservation status:", "success"));
//		} catch (Exception e) {
//			System.out.println("Error in rpc/Reservation/POST -> " + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			conn.close();
//		}
	}

}
