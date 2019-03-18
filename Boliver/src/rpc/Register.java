package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection connection = DBConnectionFactory.getConnection();
	   	 try {
	   		 JSONObject input = RpcHelper.readJSONObject(request);
	   		 String userId = input.getString("user_id");
	   		 String username = input.getString("username");
	   		 String password = input.getString("password");
	   		 String email = input.getString("email");
	   		 String firstname = input.getString("first_name");
	   		 String lastname = input.getString("last_name");
	   		 
	   		 JSONObject obj = new JSONObject();
	   		 if(connection.registerUser(userId, username, password, email, firstname, lastname)) {
	   			 obj.put("status", "you have wooooooo offically joined the rainbowBunny club, welcome!");
	   		 }else {
	   			 obj.put("status", "Wooooo! userID already exists or something else is wrong I guess ^_^");
	   		 }
	   		RpcHelper.writeJsonObject(response, obj);
	   	 } catch (Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 connection.close();
	   	 }
	}

}
