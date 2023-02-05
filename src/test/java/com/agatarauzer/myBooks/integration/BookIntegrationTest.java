package com.agatarauzer.myBooks.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.util.UriComponentsBuilder;

import com.agatarauzer.myBooks.authentication.payload.JwtResponse;
import com.agatarauzer.myBooks.authentication.payload.LoginRequest;
import com.agatarauzer.myBooks.book.BookDto;
import com.agatarauzer.myBooks.book.domain.Version;
import com.agatarauzer.myBooks.integration.H2Repository.TestH2BookRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts= {"/clear-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(scripts= {"/insert-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class BookIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestH2BookRepository h2BookRepository;
	
	private String baseUrl = "http://localhost";
	private static TestRestTemplate testRestTemplate;
	private String token;
	private HttpHeaders headers;
	
	@BeforeAll
	public static void init() {
		testRestTemplate = new TestRestTemplate();
	}
	
	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/v1");
		
		LoginRequest loginRequest = LoginRequest.builder()
				.username("adamon")
				.password("user1_password")
				.build();
		String loginUrl = baseUrl.concat("/signin");
		ResponseEntity<JwtResponse> response = testRestTemplate.postForEntity(loginUrl, loginRequest, JwtResponse.class);
		token = response.getBody().getType() + " " + response.getBody().getToken();
		
		headers = new HttpHeaders();
		headers.add("Authorization", token);
	}
	
	@Test
	public void shouldGetAllBooksForUser() {
		baseUrl = baseUrl.concat("/users/1/books");
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<List<BookDto>> result = testRestTemplate.exchange(baseUrl, HttpMethod.GET, request, 
				new ParameterizedTypeReference<List<BookDto>>(){});
				
		assertEquals(5, result.getBody().size());
		assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void shouldGetBookById_1() {
		Long userId = 1L;
		Long bookId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}").build(userId, bookId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<BookDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, BookDto.class);
		
		assertEquals("JavaTM podstawy", response.getBody().getTitle());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldGetBookById_9() {
		Long userId = 3L;
		Long bookId = 9L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}").build(userId, bookId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<BookDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, BookDto.class);
		
		assertEquals("Kubuś Puchatek", response.getBody().getTitle());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldAddBook() {
		Long userId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books").build(userId);
		
		BookDto requestBook = BookDto.builder()
				.title("Kubuś Puchatek")
				.authors("A. A. Milne")
				.isbn("8310137745, 9788310137746")
				.publisher("Wydawnictwo Nasza Księgarnia")
				.publishingDate("2016-07-21")
				.language("pl")
				.pages(155)
				.build();
		HttpEntity<BookDto> request = new HttpEntity<>(requestBook, headers);
		
		ResponseEntity<BookDto> response = testRestTemplate.postForEntity(uri, request, BookDto.class);
		
		assertEquals("Kubuś Puchatek", response.getBody().getTitle());
		assertEquals(155, response.getBody().getPages());
		assertEquals(6, h2BookRepository.findByUserId(userId).size());
		assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldUpdateBook() {
		Long userId = 1L;
		Long bookId = 3L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}").build(userId, bookId);
		
		BookDto requestBook = BookDto.builder()
				.id(bookId)
				.title("Data Stewardship An Actionable Guide to Effective Data Management and Data Governance")
				.authors("David Plotkin")
				.isbn("9780128221679, 0128221674")
				.publisher("Academic Press")
				.publishingDate("2020-10-31")
				.language("en")
				.pages(322)
				.description("Good book")
				.imageLink("link")
				.version(Version.E_BOOK)
				.build();
		HttpEntity<BookDto> request = new HttpEntity<>(requestBook, headers);

		ResponseEntity<BookDto> response = testRestTemplate.exchange(uri, HttpMethod.PUT, request, BookDto.class);
		
		String description = h2BookRepository.findById(bookId).get().getDescription();
	
		assertEquals("Good book", description);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldDeleteBook() {
		Long userId = 1L;
		Long bookId = 3L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}").build(userId, bookId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
		
		assertEquals(4, h2BookRepository.findByUserId(userId).size());
		assertTrue(response.getBody().contains("Deleted book"));
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundGivenBookId() {
		Long userId = 1L;
		Long bookId = 123L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}").build(userId, bookId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
	
		ResponseEntity<BookDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, BookDto.class);
		
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotSaveBookIfTitleAlreadyExists() {
		Long userId = 3L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books").build(userId);
		
		BookDto requestBook = BookDto.builder()
				.title("Kubuś Puchatek")
				.authors("A. A. Milne")
				.isbn("8310137745, 9788310137746")
				.publisher("Wydawnictwo Nasza Księgarnia")
				.publishingDate("2016-07-21")
				.language("pl")
				.pages(155)
				.build();
		HttpEntity<BookDto> request = new HttpEntity<>(requestBook, headers);
		
		ResponseEntity<BookDto> response = testRestTemplate.postForEntity(uri, request, BookDto.class);
		
		assertEquals(2, h2BookRepository.findByUserId(userId).size());
		assertEquals(400, response.getStatusCodeValue());
	}
}

