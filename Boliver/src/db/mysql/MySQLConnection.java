package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


import db.DBConnection;
import entity.Order;
import entity.Order.OrderBuilder;

public class MySQLConnection implements DBConnection {
	private Connection conn;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean verifyLogin(String username, String password) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "SELECT user_id FROM users WHERE username = ? AND pwd = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return true;
			}
			;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean registerUser(String userId, String username, String password, String email, String firstname,
			String lastname) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "INSERT IGNORE INTO users VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, email);
			ps.setString(5, firstname);
			ps.setString(6, lastname);

			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getDroneSpeed(String type) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return null;
		}
		try {
			String sql = "SELECT speed FROM robotType WHERE type = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, type);

			ResultSet rs = ps.executeQuery();
			String[] speed = new String[1];
			while(rs.next()) {
				speed[0] = rs.getString("speed");
				// for debug: System.out.println("speeeeeeeeed: " + speed);
			}
			return speed[0];
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Set<Order> getHistoryOrders(String userId, Integer start, Integer end) {
		
		if (conn == null) {
			return new HashSet<>();
		}
		Set<Order> historyOrders = new HashSet<>();

		try {
			String sql = "SELECT user_id, order_id, robot_id, order_status, origin, destination, e_arrival, a_arrival, create_time ,cost FROM orderHistory "
					+ "WHERE user_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);

			ResultSet rs = stmt.executeQuery();

			OrderBuilder builder = new OrderBuilder();

			while (rs.next()) {
				builder.setOrderId(rs.getString("order_id"));
				builder.setUserId(rs.getString("user_id"));
				builder.setRobotId(rs.getString("robot_id"));
				builder.setOrderStatus(rs.getString("order_status"));
				builder.setOrigin(rs.getString("origin"));
				builder.setDestination(rs.getString("destination"));
				builder.seteArrival(rs.getString("e_arrival"));
				builder.setaArrival(rs.getString("a_arrival"));
				builder.setCreateTime(rs.getString("create_time"));
				builder.setCost(rs.getString("cost"));

				historyOrders.add(builder.build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return historyOrders;
	}

	@Override
	public boolean placeOrder(Order order) {
		if (conn == null) {
			System.out.println("DB connection failed");
			return false;
		}
		try {
			String sql = "INSERT INTO CurrentOrder VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	   		PreparedStatement ps = conn.prepareStatement(sql);
	   		
	   		if (
	   				order.getOrderId() == null && 
	   				order.getRobotId() == null && 
	   				order.getUserId() == null && 
	   				order.getOrderStatus() == null && 
	   				order.getOrigin() == null && 
	   				order.getDestination() == null &&
					order.geteArrival() == null &&
					order.getCreateTime() == null &&
					order.getCost() == null
   				) {
	   			System.out.println("There is null in the data.");
	   			return false;
	   		}
	   		
	   		ps.setString(1, order.getOrderId());
	   		ps.setString(2, order.getRobotId());
	   		ps.setString(3, order.getUserId());
	   		ps.setString(4, order.getOrderStatus());
	   		ps.setString(5, order.getOrigin());
	   		ps.setString(6, order.getDestination());
	   		ps.setString(7, order.geteArrival());
	   		ps.setString(8, order.getCreateTime());
	   		ps.setString(9, order.getCost());
	   		System.out.println(ps);
	   		return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
