package mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import dto.request.CreateBookRequest;
import dto.response.BookDetailsResponse;
import model.Book;

@Mapper
public interface BookMapper {
	BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
	
	@Mapping(target = "categoryName", ignore = true)
	@Mapping(target = "isAvailable", ignore = true)
	BookDetailsResponse toBookDetailsResponse(Book book);
	
	Book toBook(BookDetailsResponse response);
	
	@Mapping(target = "id", ignore = true) // id will be created by DB
	@Mapping(target = "availableCopies", ignore = true) // logic for availableCopies will be handled in Service
	Book toBook(CreateBookRequest request);
}