package db;

import java.util.Set;

import entity.Order;
import entity.TrackOrderEntity;

public interface DBConnection {
	/**
	 * Close the connection.
	 */
	public void close();

	public boolean verifyLogin(String userId, String password);
	
	public boolean registerUser(String userId, String username, String password, String email, String firstname,
			String lastname);

	public Set<Order> getHistoryOrders(String userId, Integer start, Integer end);
	
	public boolean placeOrder(Order order);
	
	public Set<TrackOrderEntity> trackOrder(String orderId);
	
	public String getDroneSpeed(String type);
	
	public Set<Order> getCurrentOrders(String userId);
}
