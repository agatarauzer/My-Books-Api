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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.util.UriComponentsBuilder;

import com.agatarauzer.myBooks.integration.H2Repository.TestH2ReadingRepository;
import com.agatarauzer.myBooks.purchase.PurchaseDto;
import com.agatarauzer.myBooks.reading.ReadingDto;
import com.agatarauzer.myBooks.reading.domain.ReadingStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts= {"/clear-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(scripts= {"/insert-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class ReadingIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestH2ReadingRepository h2ReadingRepository;
	
	private static TestRestTemplate testRestTemplate;
	private String baseUrl = "http://localhost";

	@BeforeAll
	public static void init() {
		testRestTemplate = new TestRestTemplate();
		
	}
	
	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/v1");
	}
	
	@Test
	public void shouldGetReadingForBook() {
		Long userId = 1L;
		Long bookId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/readings").build(userId, bookId);
	    
	  	ResponseEntity<ReadingDto> response = testRestTemplate.getForEntity(uri, ReadingDto.class);
	  			
		assertEquals(LocalDate.of(2022, 2, 5), response.getBody().getStartDate());
		assertEquals(4, response.getBody().getRate());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldAddReading() {
		Long userId = 4L;
		Long bookId = 12L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/readings").build(userId, bookId);
		
		ReadingDto requestReading = ReadingDto.builder()
				.status(ReadingStatus.READED)
				.startDate(LocalDate.of(2022, 8, 27))
				.endDate(LocalDate.of(2022, 10, 6))
				.readedPages(574)
				.rate(4)
				.notes("not bad, but quite predictable")
				.build();
		
		HttpEntity<ReadingDto> request = new HttpEntity<>(requestReading);
	
		ResponseEntity<ReadingDto> response = testRestTemplate.postForEntity(uri, request, ReadingDto.class);
		
		assertEquals(574, response.getBody().getReadedPages());
		assertEquals("2022-08-27", h2ReadingRepository.findByBookId(bookId).getStartDate().toString());
		assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldUpdateReading() {
		Long userId = 1L;
		Long bookId = 1L;
		Long readingId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/readings/{readingId}").build(userId, bookId, readingId);
		
		ReadingDto requestReading = ReadingDto.builder()
				.id(readingId)
				.status(ReadingStatus.READED)
				.startDate(LocalDate.of(2022, 2, 8))
				.endDate(LocalDate.of(2022, 7, 12))
				.readedPages(600)
				.rate(3)
				.notes("good source of knowledge, but little boring")
				.build();
		
		HttpEntity<ReadingDto> request = new HttpEntity<>(requestReading);

		ResponseEntity<ReadingDto> response = testRestTemplate.exchange(uri, HttpMethod.PUT, request, ReadingDto.class);
		
		Integer rate = h2ReadingRepository.findById(readingId).get().getRate();
		String startDate = h2ReadingRepository.findById(readingId).get().getStartDate().toString();
		
		assertEquals(3, rate);
		assertEquals("2022-02-08", startDate);
		assertEquals(200, response.getStatusCodeValue());	
	}
	
	@Test
	public void shouldDeleteReading() {
		Long userId = 1L;
		Long bookId = 1L;
		Long readingId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/readings/{readingId}").build(userId, bookId, readingId);
		
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
		
		assertEquals(10, h2ReadingRepository.findAll().size());
		assertTrue(response.getBody().contains("Deleted reading"));
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundBook() {
		Long userId = 1L;
		Long bookId = 155L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/readings").build(userId, bookId);
	    
	  	ResponseEntity<String> response = testRestTemplate.getForEntity(uri, String.class);
	  			
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundReadingForBook() {
		Long userId = 4L;
		Long bookId = 12L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/readings").build(userId, bookId);
	    
	  	ResponseEntity<String> response = testRestTemplate.getForEntity(uri, String.class);
	  			
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundReadingById() {
		Long userId = 1L;
		Long bookId = 1L;
		Long readingId = 155L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/users/{userId}/books/{bookId}/readings/{readingId}").build(userId, bookId, readingId);
			
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
	
		assertEquals(404, response.getStatusCodeValue());
	}
	
	
}
