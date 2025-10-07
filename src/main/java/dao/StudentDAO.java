package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Student;
import util.DBConnectionManager;

public class StudentDAO {
	private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
		return Student.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .name(rs.getString("name"))
                .code(rs.getString("code"))
                .grade(rs.getString("grade"))
                .email(rs.getString("email"))
                .build();
	}
	
	public Student findByUserId(int userId) {
		Student student = null;
		String sqlQuery = "SELECT id, user_id, name, code, grade, email FROM students WHERE user_id = ?";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
			ps.setInt(1, userId);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					student = mapResultSetToStudent(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in findByUserId: " + e.getMessage());
		}
		
		return student;
	}
	
	public boolean saveStudent(Student student) {
		String sqlQuery = "INSERT INTO students (user_id, name, code, grade, email) VALUES (?, ?, ?, ?, ?)";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
			ps.setInt(1, student.getUserId());
			ps.setString(2, student.getName());
			ps.setString(3, student.getCode());
            ps.setString(4, student.getGrade());
            ps.setString(5, student.getEmail());
            
            int affectedRows = ps.executeUpdate();
            
            return affectedRows > 0;
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in saveStudent: " + e.getMessage());
			return false;
		}
	}
}
