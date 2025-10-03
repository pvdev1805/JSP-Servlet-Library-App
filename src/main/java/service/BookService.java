package service;

import java.util.List;
import java.util.stream.Collectors;

import dao.BookDAO;
import dao.CategoryDAO;
import dto.request.CreateBookRequest;
import dto.response.BookDetailsResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mapper.BookMapper;
import model.Book;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService {
	BookDAO bookDAO = new BookDAO();
	CategoryDAO categoryDAO = new CategoryDAO();
	BookMapper bookMapper = BookMapper.INSTANCE;
	
	public List<BookDetailsResponse> getAllBookDetails(){
		List<Book> books = bookDAO.getAllBooks();
		
		List<BookDetailsResponse> responses = books.stream().map(bookMapper::toBookDetailsResponse).collect(Collectors.toList());
		
		// Add categoryName to each response within responses
		for(int i = 0; i < books.size(); i++) {
			Book book = books.get(i);
			BookDetailsResponse response = responses.get(i);
			
			// Add categoryName
			String categoryName = categoryDAO.getCategoryNameById(book.getCategoryId());
			response.setCategoryName(categoryName);
			
			response.setAvailable(book.getAvailableCopies() > 0);
		}
		
		return responses;
	}
	
	public boolean createNewBook(CreateBookRequest request) {
		Book newBook = bookMapper.toBook(request);
		
		newBook.setAvailableCopies(newBook.getTotalCopies());
		
		return bookDAO.addBook(newBook);
	}
}
