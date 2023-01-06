package com.agatarauzer.myBooks.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.util.UriComponentsBuilder;

import com.agatarauzer.myBooks.integration.H2Repository.TestH2RentalRepository;
import com.agatarauzer.myBooks.rental.RentalDto;
import com.agatarauzer.myBooks.rental.domain.RentalStatus;
import com.agatarauzer.myBooks.security.dto.JwtResponse;
import com.agatarauzer.myBooks.security.dto.LoginRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts= {"/clear-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(scripts= {"/insert-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class RentalIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestH2RentalRepository h2RentalRepository;
	
	private static TestRestTemplate testRestTemplate;
	private String baseUrl = "http://localhost";
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
	public void shouldGetRentalForBook() {
		Long userId = 1L;
		Long bookId = 5L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/rentals").build(userId, bookId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
	    
	  	ResponseEntity<RentalDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, RentalDto.class);
	  			
		assertEquals(LocalDate.of(2022, 7, 10), response.getBody().getStartDate());
		assertEquals("to Kate", response.getBody().getName());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldAddRental() {
		Long userId = 1L;
		Long bookId = 2L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/rentals").build(userId, bookId);
	
		RentalDto requestRental = RentalDto.builder()
				.status(RentalStatus.BORROWED_FROM)
				.name("from my neighbour")
				.startDate(LocalDate.of(2022, 6, 27))
				.endDate(LocalDate.of(2022, 10, 1))
				.notes("important to give it back on time")
				.build();
		
		HttpEntity<RentalDto> request = new HttpEntity<>(requestRental, headers);
	
		ResponseEntity<RentalDto> response = testRestTemplate.postForEntity(uri, request, RentalDto.class);
		
		assertEquals("from my neighbour", response.getBody().getName());
		assertEquals("2022-06-27", h2RentalRepository.findByBookId(bookId).getStartDate().toString());
		assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldUpdateRental() {
		Long userId = 1L;
		Long bookId = 5L;
		Long rentalId = 2L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/rentals/{rentalId}").build(userId, bookId, rentalId);
		
		RentalDto requestRental = RentalDto.builder()
				.status(RentalStatus.LENDED_TO)
				.name("to Kate and Michael")
				.startDate(LocalDate.of(2022, 7, 1))
				.endDate(LocalDate.of(2022, 10, 1))
				.notes(null)
				.build();
		
		HttpEntity<RentalDto> request = new HttpEntity<>(requestRental, headers);

		ResponseEntity<RentalDto> response = testRestTemplate.exchange(uri, HttpMethod.PUT, request, RentalDto.class);
		
		String name = h2RentalRepository.findById(rentalId).get().getName();
		String startDate = h2RentalRepository.findById(rentalId).get().getStartDate().toString();
		
		assertEquals("to Kate and Michael", name);
		assertEquals("2022-07-01", startDate);
		assertEquals(200, response.getStatusCodeValue());	
	}
	
	@Test
	public void shouldDeleteRental() {
		Long userId = 3L;
		Long bookId = 10L;
		Long rentalId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/rentals/{rentalId}").build(userId, bookId, rentalId);
		HttpEntity<String> request = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
		
		assertEquals(1, h2RentalRepository.findAll().size());
		assertTrue(response.getBody().contains("Deleted rental"));
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundBook() {
		Long userId = 1L;
		Long bookId = 155L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/rentals").build(userId, bookId);
		HttpEntity<String> request = new HttpEntity<>(headers);
	    
	  	ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, String.class);
	  				
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundRentalForBook() {
		Long userId = 1L;
		Long bookId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/rentals").build(userId, bookId);
		HttpEntity<String> request = new HttpEntity<>(headers);
	    
	  	ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, String.class);
	  			
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundRentalById() {
		Long userId = 1L;
		Long bookId = 1L;
		Long rentalId = 155L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/rentals/{rentalId}").build(userId, bookId, rentalId);
		HttpEntity<String> request = new HttpEntity<>(headers);
			
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
	
		assertEquals(404, response.getStatusCodeValue());
	}
}
