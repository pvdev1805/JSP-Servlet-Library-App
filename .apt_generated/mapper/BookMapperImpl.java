package mapper;

import dto.request.CreateBookRequest;
import dto.request.UpdateBookRequest;
import dto.response.BookDetailsResponse;
import javax.annotation.processing.Generated;
import model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-08T17:32:18+1000",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDetailsResponse toBookDetailsResponse(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDetailsResponse.BookDetailsResponseBuilder bookDetailsResponse = BookDetailsResponse.builder();

        bookDetailsResponse.author( book.getAuthor() );
        bookDetailsResponse.availableCopies( book.getAvailableCopies() );
        bookDetailsResponse.description( book.getDescription() );
        bookDetailsResponse.id( book.getId() );
        bookDetailsResponse.isbn( book.getIsbn() );
        bookDetailsResponse.title( book.getTitle() );
        bookDetailsResponse.totalCopies( book.getTotalCopies() );

        return bookDetailsResponse.build();
    }

    @Override
    public Book toBook(BookDetailsResponse response) {
        if ( response == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.author( response.getAuthor() );
        book.availableCopies( response.getAvailableCopies() );
        book.description( response.getDescription() );
        book.id( response.getId() );
        book.isbn( response.getIsbn() );
        book.title( response.getTitle() );
        book.totalCopies( response.getTotalCopies() );

        return book.build();
    }

    @Override
    public Book toBook(CreateBookRequest request) {
        if ( request == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.author( request.getAuthor() );
        book.categoryId( request.getCategoryId() );
        book.description( request.getDescription() );
        book.isbn( request.getIsbn() );
        book.title( request.getTitle() );
        book.totalCopies( request.getTotalCopies() );

        return book.build();
    }

    @Override
    public Book toBook(UpdateBookRequest request) {
        if ( request == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.author( request.getAuthor() );
        book.categoryId( request.getCategoryId() );
        book.description( request.getDescription() );
        book.id( request.getId() );
        book.isbn( request.getIsbn() );
        book.title( request.getTitle() );
        book.totalCopies( request.getTotalCopies() );

        return book.build();
    }
}
