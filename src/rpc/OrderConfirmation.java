package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.PaymentInfo;

/**
 * Servlet implementation class OrderConfirmation
 */
@WebServlet("/confirmation")
public class OrderConfirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderConfirmation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("application/json");
		
		PaymentInfo pi = new PaymentInfo.PaymentInfoBuilder().setFirstname("sicheng").setLastName("wu")
				.setEmail("schuberng@gmail.com").setAddress("2004 University Ave, San Jose, 95128")
				.setPhoneNumber("3127896672").setCardNumber("xxxx-xxxx-xxxx-xxxx")
				.setMonth("12").setDate("20").setCvv("905").setCardAddress("2004 University Ave, San Jose, 95128").build();
		
		JSONObject output = new JSONObject();
		
		if (pi.getFirstname() == null || pi.getFirstname().length() == 0) {
			try {
				RpcHelper.writeJsonObject(response, new JSONObject().put("no user firstname", "failed"));
			}  catch(JSONException e) {
				System.out.println("error from /rpc/OrderConfirmation/GET -> " + e.getMessage());
		   		e.printStackTrace();
			}
			return;
		}
		
		if (pi.getLastName() == null || pi.getLastName().length() == 0) {
			try {
				RpcHelper.writeJsonObject(response, new JSONObject().put("no user lastname", "failed"));
			}  catch(JSONException e) {
				System.out.println("error from /rpc/OrderConfirmation/GET -> " + e.getMessage());
		   		e.printStackTrace();
			}
			return;
		}
		
		if (pi.getEmail() == null || pi.getEmail().length() == 0 || pi.getPhoneNumber() == null || pi.getPhoneNumber().length() == 0) {
			try {
				RpcHelper.writeJsonObject(response, new JSONObject().put("no contacts info", "failed"));
			}  catch(JSONException e) {
				System.out.println("error from /rpc/OrderConfirmation/GET -> " + e.getMessage());
		   		e.printStackTrace();
			}
			return;
		}
		
		if (pi.getCardNumber() == null || pi.getCvv() == null || pi.getDate() == null || pi.getMonth() == null || pi.getCardAddress() == null) {
			try {
				RpcHelper.writeJsonObject(response, new JSONObject().put("incomplete card info", "failed"));
			}  catch(JSONException e) {
				System.out.println("error from /rpc/OrderConfirmation/GET -> " + e.getMessage());
		   		e.printStackTrace();
			}
			return;
		}
		
		StringBuilder sb =new StringBuilder();
		String hex = Integer.toHexString(pi.hashCode());
		sb.append("rbc").append(hex);
		
		try {
			output.put("confirmation number", sb.toString());
		} catch(JSONException e) {
			System.out.println("Error in rpc/OrderPayment/GET -> "+e.getMessage());
			e.printStackTrace();
		}
		RpcHelper.writeJsonObject(response, output);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
