package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private Connection conn;	
	
    public DBConnection() {
      	 try {
      		 Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
      		 conn = DriverManager.getConnection(DBUtility.URL);
      		 
      	 } catch (Exception e) {
      		 e.printStackTrace();
      	 }
       }
       
       public void close() {
      	 if (conn != null) {
      		 try {
      			 conn.close();
      		 } catch (Exception e) {
      			 e.printStackTrace();
      		 }
      	 }
       }

       // TODO
}