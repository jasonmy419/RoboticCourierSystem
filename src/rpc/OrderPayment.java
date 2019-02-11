package rpc;

import java.io.IOException;
import java.io.PrintWriter;

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
//		doGet(request, response);
		
		try {
				JSONObject input = RpcHelper.readJSONObject(request);
				String lastName = input.getString("last_name");
				String firstName = input.getString("first_name");
				String email = input.getString("email");
				String address = input.getString("address");
				String phoneNumber = input.getString("phone_number");
				String cardNumber = input.getString("card_number");
				String month = input.getString("month");
				String date = input.getString("date");
				String cvv = input.getString("cvv");
				String cardAddress = input.getString("card_address");
			
			PaymentInfo pi = new PaymentInfo.PaymentInfoBuilder().setFirstname(lastName).setLastName(firstName)
					.setEmail(email).setAddress(address).setPhoneNumber(phoneNumber).setCardNumber(cardNumber)
					.setMonth(month).setDate(date).setCvv(cvv).setCardAddress(cardAddress).build();
			
			RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
			String str = pi.toJSONObject().toString();
			System.out.println(str);
		} catch(JSONException e) {
			System.out.println("error from /rpc/OrderPayment/POSt -> " + e.getMessage());
	   		e.printStackTrace();
		}
	}

}
