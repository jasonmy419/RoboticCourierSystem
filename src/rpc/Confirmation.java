package rpc;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;

/**
 * Servlet implementation class OrderConfirmation
 */
@WebServlet("/confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json");
		
		DBConnection conn = new DBConnection();
		JSONObject output = new JSONObject();
		
		String cardNumber = null;
		try {
			
			String userId = request.getParameter("user_id");
			cardNumber = conn.getPaymentInfo(userId);
			
			if (cardNumber == null || cardNumber.length() == 0) {
				output.put("failed","Cannot Generate Confirmation number");
			}  else {
				StringBuilder sb = new StringBuilder();
		        UUID uuid = UUID.randomUUID();
		        String str = uuid.toString();
		        sb.append(userId.hashCode()).append(str);
		        output.put("sucess",str);
			}
			
		} catch (Exception e) {
			System.out.println("Error in rpc/Confirmation/GET -> " + e.getMessage());
			e.printStackTrace();
		} finally {
			conn.close();
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
