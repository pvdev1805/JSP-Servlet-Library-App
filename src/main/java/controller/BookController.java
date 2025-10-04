package controller;

import java.io.IOException;
import java.util.List;

import dto.request.CreateBookRequest;
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
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		if(listBooksPath.equals(path)) {
			handleListBooks(request, response);
		} else if(addBookPath.equals(path)) {
			showAddBookForm(request, response);
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
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		if(addBookPath.equals(path)) {
			handleCreateBook(request, response);
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
}
