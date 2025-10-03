package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Category;
import util.DBConnectionManager;

public class CategoryDAO {
	public Category getCategoryById(int id) {
		Category category = null;
		String sqlQuery = "SELECT id, name FROM categories WHERE id = ?";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)){
			ps.setInt(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					category = Category.builder()
							.id(rs.getInt("id"))
							.name(rs.getString("name"))
							.build();
				}
			}
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in getCategoryById: " + e.getMessage());
		}
		
		return category;
	}
	
	public String getCategoryNameById(int id) {
		String name = "N/A"; // Default name if category is not found
		String sqlQuery = "SELECT name FROM categories WHERE id = ?";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)){
			ps.setInt(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					name = rs.getString("name");
				}
			}
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in getCategoryNameById: " + e.getMessage());
		}
		return name;
	}
}
