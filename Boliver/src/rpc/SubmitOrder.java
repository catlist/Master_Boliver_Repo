package rpc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Order;
import entity.Order.OrderBuilder;

/**
 * Servlet implementation class PlaceOrder
 */
@WebServlet("/submitorder")
public class SubmitOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int RANDOM = 0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection conn = DBConnectionFactory.getConnection();
		JSONObject input = RpcHelper.readJSONObject(request);	
		try {
			OrderBuilder builder = new OrderBuilder();
			//acquire parameter from front end
			//String robotType = input.getString("robot_type");
			String userId = input.getString("user_id");
			String origin = input.getString("origin");
			String destination = input.getString("destination");
			String eArrival = input.getString("e_arrival");
			String cost = input.getString("cost");
			
			//generate order ID and create time
			Date date = new Date();
			SimpleDateFormat mf = new SimpleDateFormat("yyyyMMddhhmmss");
			String createTime = mf.format(date);
			String orderId = createTime + String.format("%05d", RANDOM++);
			if (RANDOM >= 100) {
				RANDOM = 0;
			}
			
			String robotId = "1";
			
			builder.setOrderId(orderId);
			builder.setRobotId(robotId);
			builder.setUserId(userId);
			builder.setOrderStatus("1");
			builder.setOrigin(origin);
			builder.setDestination(destination);
			builder.seteArrival(eArrival);
			builder.setCreateTime(createTime);
			builder.setCost(cost);
			
			Order order = builder.build();
			JSONObject obj = new JSONObject();
			if(conn.placeOrder(order)) {
				obj.put("status", "success");
			}else {
				response.setStatus(401);
				obj.put("status", "failed");
			}
			RpcHelper.writeJsonObject(response, obj);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			conn.close();
		}
	}
}
