package mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import dto.response.BookDetailsResponse;
import model.Book;

@Mapper
public interface BookMapper {
	BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
	
	@Mapping(target = "categoryName", ignore = true)
	@Mapping(target = "isAvailable", ignore = true)
	BookDetailsResponse toBookDetailsResponse(Book book);
	
	Book toBook(BookDetailsResponse response);
}
