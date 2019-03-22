package db;

import java.util.Set;

import entity.Order;

public interface DBConnection {
	/**
	 * Close the connection.
	 */
	public void close();

	public boolean verifyLogin(String userId, String password);
	
	public boolean registerUser(String userId, String username, String password, String email, String firstname,
			String lastname);

	public Set<Order> getHistoryOrders(String userId, Integer start, Integer end);

	/**
	 * @param userId
	 * 
	 * @return set of orders
	 */
	
	public boolean placeOrder(Order order);

	/**
	 * @param order
	 * @return void
	 */
	
	public String getDroneSpeed(String type);
}
