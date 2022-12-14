package com.agatarauzer.myBooks.unit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.googleBooksSearch.GoogleBookMapper;
import com.agatarauzer.myBooks.googleBooksSearch.dto.GoogleBookBasicDto;
import com.agatarauzer.myBooks.googleBooksSearch.dto.GoogleBookDto;
import com.agatarauzer.myBooks.googleBooksSearch.dto.GoogleBookForUserDto;
import com.agatarauzer.myBooks.googleBooksSearch.dto.GoogleBooksSearchResultDto;
import com.agatarauzer.myBooks.googleBooksSearch.dto.ImageLinkDto;
import com.agatarauzer.myBooks.googleBooksSearch.dto.IsbnDto;
import com.agatarauzer.myBooks.googleBooksSearch.dto.SearchInfoDto;

@SpringBootTest
public class GoogleBookMapperTest {
	
	@Autowired
	private GoogleBookMapper googleBookMapper;
	
	private GoogleBooksSearchResultDto googleSearchResultDto; 
	private GoogleBookForUserDto googleBookForUserDto;
	
	@BeforeEach
	public void prepareTestData() {
		googleBookForUserDto = GoogleBookForUserDto.builder()
				.title("Java. Podstawy. Wydanie IX")
				.authors("Cay S. Horstmann,Gary Cornell")
				.isbn("8324677615, 9788324677610")
				.publisher("Helion")
				.publishingDate("2013-12-09")
				.language("pl")
				.pages(864)
				.description("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...")
				.imageLink("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")
				.build();
			
		IsbnDto isbnDto1 = new IsbnDto("ISBN_10", "8324677615");
		IsbnDto isbnDto2 = new IsbnDto("ISBN_13", "9788324677610");
		ImageLinkDto imageLinkDto = new ImageLinkDto("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api");
		GoogleBookDto googleBookDto = new GoogleBookDto("Java. Podstawy.", "Wydanie IX", List.of("Cay S. Horstmann", "Gary Cornell"), List.of(isbnDto1, isbnDto2), "Helion", "2013-12-09", 
				 "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", imageLinkDto);	
		SearchInfoDto searchInfo = new SearchInfoDto("empty");	
		GoogleBookBasicDto googleBookBasicDto = new GoogleBookBasicDto(googleBookDto, searchInfo);
		List<GoogleBookBasicDto> books = List.of(googleBookBasicDto);
		googleSearchResultDto = new GoogleBooksSearchResultDto(books, 1);
	}
	
	@Test
	public void sholudMapGoogleBookForUserDtoToBook() {
		Book book = googleBookMapper.mapToBook(googleBookForUserDto);
		
		assertEquals(googleBookForUserDto.getTitle(), book.getTitle());
		assertEquals(googleBookForUserDto.getAuthors(), book.getAuthors());
		assertEquals(googleBookForUserDto.getIsbn(), book.getIsbn());
		assertEquals(googleBookForUserDto.getPublisher(), book.getPublisher());
		assertEquals(googleBookForUserDto.getPublishingDate(), book.getPublishingDate());
		assertEquals(googleBookForUserDto.getLanguage(), book.getLanguage());
		assertEquals(googleBookForUserDto.getPages(), book.getPages());
		assertEquals(googleBookForUserDto.getDescription(), book.getDescription());
	}
	
	@Test
	public void shouldMapGoogleSearchResultDtoToGoogleBookForUserDtoList() {
		List<GoogleBookForUserDto> booksForUser =  googleBookMapper.mapToGoogleBookForUserDtoList(googleSearchResultDto);
		
		assertEquals("Java. Podstawy. Wydanie IX", booksForUser.get(0).getTitle());
		assertEquals("Cay S. Horstmann, Gary Cornell", booksForUser.get(0).getAuthors());
		assertEquals("8324677615, 9788324677610", booksForUser.get(0).getIsbn());
		assertEquals("Helion", booksForUser.get(0).getPublisher());
		assertEquals("2013-12-09", booksForUser.get(0).getPublishingDate());
		assertEquals("pl", booksForUser.get(0).getLanguage());
		assertEquals(864, booksForUser.get(0).getPages());
		assertEquals("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", booksForUser.get(0).getDescription());
	}
	
	@Test
	public void shouldMapGoogleSearchResultDtoToGoogleBookForUserDto_WithMissingFields() {
		GoogleBookDto googleBookDto = new GoogleBookDto("Java. Podstawy.", null, null, null, null, null, null, 0, null, null);	
		SearchInfoDto searchInfo = new SearchInfoDto("empty");	
		GoogleBookBasicDto googleBookBasicDto = new GoogleBookBasicDto(googleBookDto, searchInfo);
		List<GoogleBookBasicDto> books = List.of(googleBookBasicDto);
		googleSearchResultDto = new GoogleBooksSearchResultDto(books, 1);
		
		List<GoogleBookForUserDto> googleBookWithMissingFields = googleBookMapper.mapToGoogleBookForUserDtoList(googleSearchResultDto);
		
		assertEquals("Java. Podstawy.", googleBookWithMissingFields.get(0).getTitle());
		assertEquals(null, googleBookWithMissingFields.get(0).getAuthors());
		assertEquals(null, googleBookWithMissingFields.get(0).getIsbn());
		assertEquals(null, googleBookWithMissingFields.get(0).getPublisher());
		assertEquals(null, googleBookWithMissingFields.get(0).getPublishingDate());
		assertEquals(null, googleBookWithMissingFields.get(0).getLanguage());
		assertEquals(0, googleBookWithMissingFields.get(0).getPages());
		assertEquals("empty", googleBookWithMissingFields.get(0).getDescription());
	}
}
