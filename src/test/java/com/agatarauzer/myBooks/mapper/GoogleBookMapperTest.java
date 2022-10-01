package com.agatarauzer.myBooks.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookBasicDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBooksSearchResultDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.ImageLinkDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.IsbnDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.SearchInfo;

@SpringBootTest
public class GoogleBookMapperTest {
	
	@Autowired
	private GoogleBookMapper googleBookMapper;
	
	@Test
	public void sholudMapGoogleBookForUserDtoToBook() {
		GoogleBookForUserDto googleBookForUserDto = new GoogleBookForUserDto("Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api");
		
		Book book = googleBookMapper.mapToBook(googleBookForUserDto);
		
		assertEquals(book.getTitle(), googleBookForUserDto.getTitle());
		assertEquals(book.getAuthors(), googleBookForUserDto.getAuthors());
		assertEquals(book.getISBN(), googleBookForUserDto.getISBN());
		assertEquals(book.getPublisher(), googleBookForUserDto.getPublisher());
		assertEquals(book.getPublishingDate(), googleBookForUserDto.getPublishingDate());
		assertEquals(book.getLanguage(), googleBookForUserDto.getLanguage());
		assertEquals(book.getPages(), googleBookForUserDto.getPages());
		assertEquals(book.getDescription(), googleBookForUserDto.getDescription());
	}
	
	@Test
	public void shouldMapGoogleSearchResultDtoToGoogleBookForUserDtoList() {
		IsbnDto isbnDto1 = new IsbnDto("ISBN_10", "8324677615");
		IsbnDto isbnDto2 = new IsbnDto("ISBN_13", "9788324677610");
		ImageLinkDto imageLinkDto = new ImageLinkDto("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api");
		GoogleBookDto googleBookDto = new GoogleBookDto("Java. Podstawy.", "Wydanie IX", List.of("Cay S. Horstmann", "Gary Cornell"), "Helion", "2013-12-09", "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				List.of(isbnDto1, isbnDto2), 864, "pl", imageLinkDto);	
		
		SearchInfo searchInfo = new SearchInfo("empty");	
		GoogleBookBasicDto googleBookBasicDto = new GoogleBookBasicDto(googleBookDto, searchInfo);
		
		List<GoogleBookBasicDto> books = List.of(googleBookBasicDto);
		GoogleBooksSearchResultDto googleSearchResultDto = new GoogleBooksSearchResultDto(books, 1);
		
		List<GoogleBookForUserDto> booksForUser =  googleBookMapper.mapToGoogleBookForUserDtoList(googleSearchResultDto);
		
		assertEquals("Java. Podstawy. Wydanie IX", booksForUser.get(0).getTitle());
		assertEquals("Cay S. Horstmann,Gary Cornell", booksForUser.get(0).getAuthors());
		assertEquals("8324677615, 9788324677610", booksForUser.get(0).getISBN());
		assertEquals("Helion", booksForUser.get(0).getPublisher());
		assertEquals("2013-12-09", booksForUser.get(0).getPublishingDate());
		assertEquals("pl", booksForUser.get(0).getLanguage());
		assertEquals(864, booksForUser.get(0).getPages());
		assertEquals("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", booksForUser.get(0).getDescription());
	}
}
