package rpc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.TrackOrderEntity;

/**
 * Servlet implementation class TrackOrder
 */
@WebServlet("/trackorder")
public class TrackOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrackOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		DBConnection conn = DBConnectionFactory.getConnection();
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
			String orderId = input.getString("order_id");
			JSONArray array = new JSONArray();
			Set<TrackOrderEntity> orders = conn.trackOrder(orderId);
			for (TrackOrderEntity order : orders) {
				JSONObject obj = order.toJSONObject();
				array.put(obj);
			}

			RpcHelper.writeJsonArray(response, array);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		JSONObject object = null;
		RpcHelper.writeJsonObject(response, object);
	}


}
