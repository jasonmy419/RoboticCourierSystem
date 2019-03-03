package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;

/**
 * Servlet implementation class Tracking
 */
@WebServlet("/tracking")
public class Tracking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tracking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		JSONObject obj = new JSONObject();
		try {
			obj.put("response","please send request from POST");
		} catch(JSONException e) {
			System.out.println("Error in rpc/Tracking/GET -> "+e.getMessage());
			e.printStackTrace();
		}
		RpcHelper.writeJsonObject(response, obj);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DBConnection conn = new DBConnection();
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
			String orderId = input.getString("order_id");
			String s = conn.getStatus(orderId);
			JSONObject obj = new JSONObject();
			obj.put("Delivery", s);
			RpcHelper.writeJsonObject(response, obj);
		} catch (Exception e) {
			System.out.println("Error in rpc/Tracking/POST -> "+e.getMessage());
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

}
