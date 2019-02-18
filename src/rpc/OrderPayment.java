package rpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import entity.Payment;

/**
 * Servlet implementation class OrderPayment
 */
@WebServlet("/checkout")
public class OrderPayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderPayment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		// mock data
		JSONArray array = new JSONArray();
		try {
			array.put(new JSONObject().put("username", "youtube"));
			array.put(new JSONObject().put("username", "amazon"));
			array.put(new JSONObject().put("username", "google"));
		} catch(JSONException e) {
			System.out.println("Error in rpc/OrderPayment/GET -> "+e.getMessage());
			e.printStackTrace();
		}
		RpcHelper.writeJsonArray(response, array);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DBConnection conn = new DBConnection();
		try {
				JSONObject input = RpcHelper.readJSONObject(request);
				String userId = request.getParameter("user_id");
				
				boolean flag = conn.setPaymentInfo(userId,input);
							
				if (flag) {
					RpcHelper.writeJsonObject(response, new JSONObject().put("payment status:", "SUCCESS"));
				}  else {
					RpcHelper.writeJsonObject(response, new JSONObject().put("payment status:", "failed"));
				}
				
		} catch(Exception e) {
			System.out.println("error from /rpc/OrderPayment/Post -> " + e.getMessage());
	   		e.printStackTrace();
		}  finally {
			conn.close();
	   	 }
	}

}
