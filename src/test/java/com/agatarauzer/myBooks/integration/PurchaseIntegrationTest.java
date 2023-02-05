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

import com.agatarauzer.myBooks.authentication.payload.JwtResponse;
import com.agatarauzer.myBooks.authentication.payload.LoginRequest;
import com.agatarauzer.myBooks.integration.H2Repository.TestH2PurchaseRepository;
import com.agatarauzer.myBooks.purchase.PurchaseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts= {"/clear-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(scripts= {"/insert-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class PurchaseIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestH2PurchaseRepository h2PurchaseRepository;
	
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
	public void shouldGetPurchaseForBook() {
		Long userId = 1L;
		Long bookId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/purchases").build(userId, bookId);
	    HttpEntity<String> request = new HttpEntity<String>(headers);
	    
	  	ResponseEntity<PurchaseDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, PurchaseDto.class);
	    
		assertEquals(79.50, response.getBody().getPrice());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldAddPurchaseForBook() {
		Long userId = 3L;
		Long bookId = 10L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/purchases").build(userId, bookId);
		
		PurchaseDto requestPurchase = PurchaseDto.builder()
			.price(35.70)
			.purchaseDate(LocalDate.of(2022, 7, 12))
			.boughtFrom("empik.com")
			.build();
		HttpEntity<PurchaseDto> request = new HttpEntity<>(requestPurchase, headers);
	
		ResponseEntity<PurchaseDto> response = testRestTemplate.exchange(uri, HttpMethod.POST, request, PurchaseDto.class);
		
		assertEquals(35.70, response.getBody().getPrice());
		assertEquals("empik.com", h2PurchaseRepository.findByBookId(bookId).getBoughtFrom());
		assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldUpdatePurchase() {
		Long userId = 1L;
		Long bookId = 2L;
		Long purchaseId = 2L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/purchases/{purchaseId}").build(userId, bookId, purchaseId);
		
		PurchaseDto requestPurchase = PurchaseDto.builder()
			.price(60.80)
			.purchaseDate(LocalDate.of(2022, 8, 23))
			.boughtFrom("empik.com")
			.build();
		HttpEntity<PurchaseDto> request = new HttpEntity<>(requestPurchase, headers);

		ResponseEntity<PurchaseDto> response = testRestTemplate.exchange(uri, HttpMethod.PUT, request, PurchaseDto.class);
		
		Double price = h2PurchaseRepository.findById(purchaseId).get().getPrice();
		String boughtFrom = h2PurchaseRepository.findById(purchaseId).get().getBoughtFrom();
		
		assertEquals(60.80, price);
		assertEquals("empik.com", boughtFrom);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldDeletePurchase() {
		Long userId = 1L;
		Long bookId = 1L;
		Long purchaseId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/purchases/{purchaseId}").build(userId, bookId, purchaseId);
		HttpEntity<String> request = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
		
		assertEquals(8, h2PurchaseRepository.findAll().size());
		assertTrue(response.getBody().contains("Deleted purchase"));
		assertEquals(200, response.getStatusCodeValue());	
	}
	
	@Test
	public void shouldNotFoundBook() {
		Long userId = 3L;
		Long bookId = 144L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/purchases").build(userId, bookId);
	    HttpEntity<String> request = new HttpEntity<String>(headers);
	    
	  	ResponseEntity<PurchaseDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, PurchaseDto.class);
	    
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundPurchaseForBook() {
		Long userId = 3L;
		Long bookId = 10L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/purchases").build(userId, bookId);
	    HttpEntity<String> request = new HttpEntity<String>(headers);
	    
	  	ResponseEntity<PurchaseDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, PurchaseDto.class);
	    
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundPurchaseById() {
		Long userId = 1L;
		Long bookId = 1L;
		Long purchaseId = 144L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/purchases/{purchaseId}").build(userId, bookId, purchaseId);
		HttpEntity<String> request = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
	
		assertEquals(404, response.getStatusCodeValue());
	}
}




