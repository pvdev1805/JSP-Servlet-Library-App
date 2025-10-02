package dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDetailsResponse {
	int id;
	String title;
	String author;
	String isbn;
	String description;
	int totalCopies;
	int availableCopies;
	String categoryName;
	boolean isAvailable; // true if availableCopies > 0
}
