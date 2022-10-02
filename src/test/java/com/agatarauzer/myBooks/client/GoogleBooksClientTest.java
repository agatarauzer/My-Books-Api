package com.agatarauzer.myBooks.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.agatarauzer.myBooks.config.GoogleBooksApiConfig;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookBasicDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBooksSearchResultDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.ImageLinkDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.IsbnDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.SearchInfo;
import com.agatarauzer.myBooks.mapper.GoogleBookMapper;

@ExtendWith(MockitoExtension.class)
public class GoogleBooksClientTest {
	
	@InjectMocks
	private GoogleBooksClient googleBooksClient;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private GoogleBooksApiConfig config;
	
	@Mock
	private GoogleBookMapper googleBookMapper;
	
	private GoogleBooksSearchResultDto googleBooksSearchResultDto;
	private List<GoogleBookForUserDto> googleBookForUserDtoList;
	
	@BeforeEach
	public void prepareTestData() {
		IsbnDto isbnDto1 = new IsbnDto("ISBN_10", "8324677615");
		IsbnDto isbnDto2 = new IsbnDto("ISBN_13", "9788324677610");
		ImageLinkDto imageLinkDto = new ImageLinkDto("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api");
		GoogleBookDto googleBookDto = new GoogleBookDto("Java. Podstawy.", "Wydanie IX", List.of("Cay S. Horstmann", "Gary Cornell"), List.of(isbnDto1, isbnDto2), "Helion", "2013-12-09", "pl", 864, 
				"Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", imageLinkDto);
		SearchInfo searchInfo = new SearchInfo("empty");	
		GoogleBookBasicDto googleBookBasicDto = new GoogleBookBasicDto(googleBookDto, searchInfo);
		List<GoogleBookBasicDto> books = List.of(googleBookBasicDto);
		googleBooksSearchResultDto = new GoogleBooksSearchResultDto(books, 1);
		
		GoogleBookForUserDto googleBookForUserDto = new GoogleBookForUserDto(
				"Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "8324677615, 9788324677610", "Helion", "2013-12-09", "pl", 864,
				"Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				"http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api");
		googleBookForUserDtoList = List.of(googleBookForUserDto);
	}
	
	@Test
	public void sholudReturnBooksList() throws URISyntaxException {		
		when(config.getGoogleBooksApiEndpoint()).thenReturn("http://test.com");
		URI uri = new URI("http://test.com?q=java+podstawy");
		when(googleBookMapper.mapToGoogleBookForUserDtoList(googleBooksSearchResultDto)).thenReturn(googleBookForUserDtoList);
		when(restTemplate.getForObject(uri, GoogleBooksSearchResultDto.class )).thenReturn(googleBooksSearchResultDto);
		
		List<GoogleBookForUserDto> booksDtoList = googleBooksClient.getBooksFromSearch("java+podstawy");
		
		assertEquals(1, booksDtoList.size());
		assertEquals("Java. Podstawy. Wydanie IX", booksDtoList.get(0).getTitle());
		assertEquals("Cay S. Horstmann,Gary Cornell", booksDtoList.get(0).getAuthors());
		assertEquals("8324677615, 9788324677610", booksDtoList.get(0).getISBN());
		assertEquals("Helion", booksDtoList.get(0).getPublisher());
		assertEquals("2013-12-09", booksDtoList.get(0).getPublishingDate());
		assertEquals("pl", booksDtoList.get(0).getLanguage());
		assertEquals(864, booksDtoList.get(0).getPages());
		assertEquals("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", booksDtoList.get(0).getDescription());
		assertEquals("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", booksDtoList.get(0).getImageLink());
	}
}


