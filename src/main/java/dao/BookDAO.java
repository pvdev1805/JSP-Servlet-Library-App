package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import util.DBConnectionManager;

public class BookDAO {
	private Book mapResultSetToBook(ResultSet rs) throws SQLException {
		return Book.builder()
				.id(rs.getInt("id"))
				.categoryId(rs.getInt("category_id"))
				.title(rs.getString("title"))
				.author(rs.getString("author"))
				.isbn(rs.getString("isbn"))
				.description(rs.getString("description"))
				.totalCopies(rs.getInt("total_copies"))
				.availableCopies(rs.getInt("available_copies"))
				.build();
	}
	
	public List<Book> getAllBooks(){
		String sqlQuery = "SELECT id, category_id, title, author, isbn, description, total_copies, available_copies FROM books";
		List<Book> books = new ArrayList<>();
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery);
				ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				books.add(mapResultSetToBook(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in getAllBooks: " + e.getMessage());
		}
		return books;
	}
	
	public Book getBookById(int id) throws SQLException {
		Book book = null;
		String sqlQuery = "SELECT id, category_id, title, author, isbn, description, total_copies, available_copies FROM books WHERE id = ?";
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)){
			ps.setInt(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					book = mapResultSetToBook(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in getBookById: " + e.getMessage());
		}
		return book;
	}
	
	public boolean addBook(Book book) throws SQLException {
		String sqlQuery = "INSERT INTO books (category_id, title, author, isbn, description, total_copies, available_copies) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
		boolean rowInserted = false;
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)){
			ps.setInt(1, book.getCategoryId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getDescription());
            ps.setInt(6, book.getTotalCopies());
            ps.setInt(7, book.getAvailableCopies());
            
            rowInserted = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in addBook: " + e.getMessage());
		}
		return rowInserted;
	}
	
	public boolean updateBookDetails(Book book) throws SQLException {
		String sqlQuery = "UPDATE books SET category_id = ?, title = ?, author = ?, description = ?, isbn = ? " +
                "WHERE id = ?";
		boolean rowUpdated = false;
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)){
			ps.setInt(1, book.getCategoryId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getIsbn());
            ps.setInt(6, book.getId());

            rowUpdated = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in updateBookDetails: " + e.getMessage());
		}
		return rowUpdated;
	}
	
	public boolean deleteBook(int id) throws SQLException {
		String sqlQuery = "DELETE FROM books WHERE id = ?";
		boolean rowDeleted = false;
		
		try(Connection conn = DBConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery)){
			ps.setInt(1, id);
			
			rowDeleted = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error: SQL Error in deleteBook: " + e.getMessage());
		}
		return rowDeleted;
	}
}
