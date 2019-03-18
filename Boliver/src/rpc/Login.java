package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			HttpSession session = request.getSession(false);
			JSONObject obj = new JSONObject();
			
			if(session != null) {
				String username = session.getAttribute("username").toString();
				String sessionId = session.getId();
				int sessionInterval = session.getMaxInactiveInterval();
				obj.put("status", "Oh, there is your session. Here, take it and please take care of it")
				   .put("sessionId", sessionId)
				   .put("username", username)
				   .put("your session lasts for in seconds", sessionInterval);
			} else {
				response.setStatus(403);
				obj.put("status", "Where is your session? I must have misplaced it...");
			}
			RpcHelper.writeJsonObject(response, obj);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection connection = DBConnectionFactory.getConnection();
	   	 try {
	   		 JSONObject input = RpcHelper.readJSONObject(request);
	   		 String username = input.getString("username");
	   		 String password = input.getString("password");
	   		 
	   		 JSONObject obj = new JSONObject();
	   		 if(connection.verifyLogin(username, password)) {
	   			 HttpSession session = request.getSession();
	   			 session.setAttribute("username", username);
	   			 session.setMaxInactiveInterval(600);
	   			 obj.put("status", "wooooo login successfully!").put("username", username);
	   		 }else {
	   			 response.setStatus(401);
	   			 obj.put("status", "User Doesn't exist");
	   		 }
	   		RpcHelper.writeJsonObject(response, obj);
	   	 } catch (Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 connection.close();
	   	 }
	}

}
