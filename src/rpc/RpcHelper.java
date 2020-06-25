package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class RpcHelper {

	// Writes a JSONArray to http response.
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException {
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		out.print(array);
		out.close();

	}

	// Writes a JSONObject to http response.
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.close();

	}
	
	public static void writeJsonObjectWithCookie(HttpServletResponse response, JSONObject obj) throws IOException {
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Origin", "http://findyuma.com.s3-website-us-west-1.amazonaws.com");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.close();

	}

	public static JSONObject readJSONObject(HttpServletRequest request) {

		StringBuilder sb = new StringBuilder();

		try (BufferedReader reader = request.getReader()) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return new JSONObject(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JSONObject();
	}

}
