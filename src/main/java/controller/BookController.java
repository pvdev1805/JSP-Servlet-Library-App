package controller;

import java.io.IOException;
import java.util.List;

import dto.request.CreateBookRequest;
import dto.request.UpdateBookRequest;
import dto.response.BookDetailsResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;

@WebServlet(urlPatterns = {"/books/*"})
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final BookService bookService = new BookService();
	private final String listBooksPath = "/";
	private final String addBookPath = "/new";
	private final String editBookPath = "/edit";
	private final String deleteBookPath = "/delete";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		if(listBooksPath.equals(path)) {
			handleListBooks(request, response);
		} else if(addBookPath.equals(path)) {
			showAddBookForm(request, response);
		} else if(editBookPath.equals(path)) {
			showEditBookForm(request, response);
		} else if(deleteBookPath.equals(path)) {
			handleDeleteBook(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void handleListBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<BookDetailsResponse> bookList = bookService.getAllBookDetails();
			
			request.setAttribute("books", bookList);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/books/book-list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.err.println("Error: Error listing books at handleListBooks: " + e.getMessage());
			request.setAttribute("errorMessage", "Error: Could not losf book list.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void showAddBookForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/books/book-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showEditBookForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int bookId = Integer.parseInt(request.getParameter("id"));
			
			BookDetailsResponse bookDetailsResponse = bookService.getBookDetailsById(bookId);
			
			if(bookDetailsResponse == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Error: Book Not Found.");
				return;
			}
			
			request.setAttribute("book", bookDetailsResponse);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/books/book-form.jsp");
			dispatcher.forward(request, response);
		} catch (NumberFormatException | NullPointerException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Invalid Book ID");
		}
	}
	
	private void handleDeleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int bookId = Integer.parseInt(request.getParameter("id"));
			
			boolean isSuccess = bookService.deleteBook(bookId);
			
			if(isSuccess) {
				response.sendRedirect(request.getContextPath() + listBooksPath);
			} else {
				request.getSession().setAttribute("error", "Error: Cannot delete book. It may be currently loaned.");
	            response.sendRedirect(request.getContextPath() + listBooksPath);
			}
			
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Book ID format.");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		if(addBookPath.equals(path)) {
			handleCreateBook(request, response);
		} else if(editBookPath.equals(path)) {
			handleUpdateBook(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void handleCreateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String description = request.getParameter("description");
		
		try {
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			int totalCopies = Integer.parseInt(request.getParameter("totalCopies"));
			
			CreateBookRequest createBookRequest = CreateBookRequest.builder()
					.title(title)
					.author(author)
					.isbn(isbn)
					.description(description)
					.categoryId(categoryId)
					.totalCopies(totalCopies)
					.build();
			
			boolean isSuccess = bookService.createNewBook(createBookRequest);
			
			if(isSuccess) {
				response.sendRedirect(request.getContextPath() + listBooksPath);
			} else {
				request.setAttribute("error", "Error: Failed to add book.");
				request.setAttribute("title", title);
				request.setAttribute("author", author);
				request.setAttribute("isbn", isbn);
				request.setAttribute("description", description);
				request.setAttribute("categoryId", categoryId);
				request.setAttribute("totalCopies", totalCopies);
				showAddBookForm(request, response);
			}
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Error: Total Copies and Category ID must be valid numbers.");
		} catch (Exception e) {
			System.err.println("Error: Error processing handleCreateBook: " + e.getMessage());
			request.setAttribute("error", "Error: An unexpected error occurred.");
			showAddBookForm(request, response);
		}
	}
	
	private void handleUpdateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String description = request.getParameter("description");
		
		try {
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			int totalCopies = Integer.parseInt(request.getParameter("totalCopies"));
			
			UpdateBookRequest updateRequest = UpdateBookRequest.builder()
					.id(bookId)
					.title(title)
					.author(author)
					.isbn(isbn)
					.description(description)
					.categoryId(categoryId)
					.totalCopies(totalCopies)
					.build();
			
			boolean isSuccess = bookService.updateBook(updateRequest);
			
			if(isSuccess) {
				response.sendRedirect(request.getContextPath() + listBooksPath);
			} else {
				request.setAttribute("error", "Error: Failed to update book");
				showEditBookForm(request, response);
			}
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Invalid numerical input for update.");
		}
	}
}
