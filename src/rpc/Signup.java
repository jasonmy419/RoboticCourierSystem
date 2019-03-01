package rpc;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.DBConnection;
import entity.Address;
import entity.Address.AddressBuilder;
import entity.State;
import entity.User;
import entity.User.UserBuilder;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Signup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DBConnection connection = new DBConnection();
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
			String userId = input.getString("user_id");
			String password = input.getString("password");
			String firstName = input.getString("first_name");
			String lastName = input.getString("last_name");
			String streetNum = input.getString("street_num");
			String streetName = input.getString("street_name");
			String city = input.getString("city");
			// String state = input.getString("state");
			String zipCode = input.getString("zip_code");
			
			// Addr
			String addressId = UUID.randomUUID().toString();
			Address addr = new AddressBuilder()
					.setAddressId(addressId)
					.setStreetNum(streetNum)
					.setStreetName(streetName)
					.setCity(city)
					.setState(State.CA) // Assume in CA
					.build();
			
			// User
			User user = new UserBuilder()
					.userId(userId)
					.password(password)
					.firstName(firstName)
					.lastName(lastName)
					.address(addressId)
					.zipCode(zipCode)
					.build();
			
			

			JSONObject obj = new JSONObject();
			if (connection.signup(user, addr)) {
				/*
				 * HttpSession session = request.getSession(); // Create session
				 * session.setAttribute("user_id", userId); // Binds userId obj to session
				 * session.setMaxInactiveInterval(6000);
				 */
				obj.put("status", "OK").put("user_id", userId).put("name", connection.getFullname(userId));
			} else {
				response.setStatus(401);
				obj.put("status", "Invalid Information/User_id already exist");
			}

			RpcHelper.writeJsonObject(response, obj);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(401); // Catch request missing field
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}
