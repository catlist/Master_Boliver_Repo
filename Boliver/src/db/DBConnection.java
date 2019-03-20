package db;

import java.util.Set;

import entity.Order;

public interface DBConnection {
	/**
	 * Close the connection.
	 */
	public void close();

	/**
	 * Insert the favorite items for a user.
	 * 
	 * @param userId
	 * @param itemIds
	 */
	
	public String getFullname(String userId);

	/**
	 * Return whether the credential is correct. (This is not needed for main
	 * course, just for demo and extension)
	 * 
	 * @param userId
	 * @param password
	 * @return boolean
	 */
	public boolean verifyLogin(String userId, String password);
	
	/**
	 * 
	 * @param userId
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @return
	 */
	public boolean registerUser(String userId, String username, String password, String email, String firstname,
			String lastname);

	public Set<Order> getHistoryOrders(String userId, Integer start, Integer end);

	/**
	 * Gets categories based on item id
	 * 
	 * @param itemId
	 * @return set of orders
	 */
}
