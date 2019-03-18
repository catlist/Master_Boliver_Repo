package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

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
	
	@Override // this method is never called, was used to test something in Login.java, but well, I will leave it here for reference
	public String getFullname(String username) {
		if(conn == null) {
			return "";
		}
		String name = "";
		try {
			String sql = "SELECT fname, lname FROM users WHERE username = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				name = rs.getString("fname") + " " + rs.getString("lname");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return name;
	}
	
	@Override
	public boolean verifyLogin(String username, String password) {
		if(conn == null) {
			return false;
		}
		try {
			String sql = "SELECT user_id FROM users WHERE username = ? AND pwd = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				return true;
			};
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean registerUser(String userId, String username, String password, String email, String firstname, String lastname) {
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

}
