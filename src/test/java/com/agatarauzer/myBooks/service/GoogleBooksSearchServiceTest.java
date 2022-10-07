package com.agatarauzer.myBooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agatarauzer.myBooks.client.GoogleBooksClient;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;

@ExtendWith(MockitoExtension.class)
public class GoogleBooksSearchServiceTest {
	
	@InjectMocks
	private GoogleBooksSearchService searchService;
	
	@Mock
	private GoogleBooksClient googleBooksClient;
	
	
	@Test
	public void shouldGetBooksBySearchedPhrase() {
		GoogleBookForUserDto book = new GoogleBookForUserDto("Good Economics, Bad Economics Eight Ways We Get the World Wrong and How to Set It Right", "Abhijit V. Banerjee, Esther Duflo",
				"1610399501, 9781610399500", "PublicAffairs", "2019-11-12", "en", 320, "Two prize-winning economists show how economics, when done right, can help us solve the thorniest social and political problems",
				"http://books.google.com/books/content?id=GUnWwgEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api");
		List<GoogleBookForUserDto> books = List.of(book);
		when(googleBooksClient.getBooksFromSearch("good economics")).thenReturn(books);
		
		List<GoogleBookForUserDto> searchedBooks = searchService.searchBookByPhrase("good economics");
		
		assertEquals(1, searchedBooks.size());		
	}
}