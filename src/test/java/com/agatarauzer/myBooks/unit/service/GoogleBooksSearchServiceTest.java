package com.agatarauzer.myBooks.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agatarauzer.myBooks.googleBooksSearch.GoogleBooksClient;
import com.agatarauzer.myBooks.googleBooksSearch.GoogleBooksSearchService;
import com.agatarauzer.myBooks.googleBooksSearch.dto.GoogleBookForUserDto;

@ExtendWith(MockitoExtension.class)
public class GoogleBooksSearchServiceTest {
	
	@InjectMocks
	private GoogleBooksSearchService searchService;
	
	@Mock
	private GoogleBooksClient googleBooksClient;
	
	
	@Test
	public void shouldGetBooksBySearchedPhrase() {
		GoogleBookForUserDto book = GoogleBookForUserDto.builder()
				.title("Good Economics, Bad Economics Eight Ways We Get the World Wrong and How to Set It Right")
				.authors("Abhijit V. Banerjee, Esther Duflo")
				.isbn("1610399501, 9781610399500")
				.publisher("PublicAffairs")
				.publishingDate("2019-11-12")
				.language("en")
				.pages(320)
				.description("Two prize-winning economists show how economics, when done right, can help us solve the thorniest social and political problems")
				.imageLink("http://books.google.com/books/content?id=GUnWwgEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api")
				.build();
		List<GoogleBookForUserDto> books = List.of(book);
		when(googleBooksClient.getBooksFromSearch("good economics")).thenReturn(books);
		
		List<GoogleBookForUserDto> searchedBooks = searchService.searchBookByPhrase("good economics");
		
		assertEquals(1, searchedBooks.size());		
	}
}
