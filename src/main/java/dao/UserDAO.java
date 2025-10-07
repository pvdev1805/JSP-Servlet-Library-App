package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import util.DBConnectionManager;

public class UserDAO {
	private User mapResultSetToUser(ResultSet rs) throws SQLException {
		return User.builder()
				.id(rs.getInt("id"))
                .username(rs.getString("username"))
                .hashedPassword(rs.getString("hashed_password"))
                .role(rs.getString("role"))
                .build();
	}
	
	public User findByUsername(String username) {
		User user = null;
		String sqlQuery = "SELECT id, username, hashed_password, role FROM users WHERE username = ?";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
			ps.setString(1, username);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					user = mapResultSetToUser(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in findByUsername: " + e.getMessage());
		}
		
		return user;
	}
	
	public User findById(int id) {
		User user = null;
		String sqlQuery = "SELECT id, username, hashed_password, role FROM users WHERE id = ?";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
			ps.setInt(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					user = mapResultSetToUser(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in findById: " + e.getMessage());
		}
		
		return user;
	}
	
	public int saveUser(User user) {
		int newId = -1;
		String sqlQuery = "INSERT INTO users (username, hashed_password, role) VALUES (?, ?, ?)";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getHashedPassword());
			ps.setString(3, user.getRole());
			
			int affectedRows = ps.executeUpdate();
			
			if(affectedRows > 0) {
				try(ResultSet rs = ps.getGeneratedKeys()){
					if(rs.next()) {
						newId = rs.getInt(1);
					}
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in saveUser: " + e.getMessage());
		}
		return newId;
	}
}
