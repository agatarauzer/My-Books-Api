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
import com.agatarauzer.myBooks.integration.H2Repository.TestH2UserRepository;
import com.agatarauzer.myBooks.user.dto.UserDto;
import com.agatarauzer.myBooks.user.dto.UserFullInfoDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
	@Sql(scripts= {"/clear-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(scripts= {"/insert-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class UserForAdminIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestH2UserRepository h2UserRepository;
	
	private String baseUrl = "http://localhost";
	private static TestRestTemplate testRestTemplate;
	String token;
	HttpHeaders headers;
	
	
	@BeforeAll
	public static void init() {
		testRestTemplate = new TestRestTemplate();
	}
	
	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/v1");
		
		LoginRequest loginRequest = LoginRequest.builder()
				.username("admin")
				.password("password_admin")
				.build();
		String loginUrl = baseUrl.concat("/signin");
		ResponseEntity<JwtResponse> response = testRestTemplate.postForEntity(loginUrl, loginRequest, JwtResponse.class);
		token = response.getBody().getType() + " " + response.getBody().getToken();
		
		headers = new HttpHeaders();
		headers.add("Authorization", token);

	}
	
	// not working, deserialization problem
	@Test
	public void shouldGetAllUsers() {
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/admin/users")
				.queryParam("page", 0)
				.queryParam("size", 10)
				.queryParam("sortBy", "registration_date")
				.queryParam("sortDir", "desc")
				.build().encode().toUri();	
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<List<UserFullInfoDto>> result = testRestTemplate
				.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<UserFullInfoDto>>(){});
		
		assertEquals(5, result.getBody().size());
		assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void shouldGetUserById_1() {
		Long userId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/admin/users/{userId}").build(userId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<UserDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, UserDto.class);
		
		assertEquals("Monday", response.getBody().getLastName());
		assertEquals("user1@test.com", response.getBody().getEmail());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldGetUserById_3() {
		Long userId = 3L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/admin/users/{userId}").build(userId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<UserDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, UserDto.class);
		
		assertEquals("Wednesday", response.getBody().getLastName());
		assertEquals("user3@test.com", response.getBody().getEmail());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldUpdateUser() {
		Long userId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/admin/users/{userId}").build(userId);
		UserDto requestUser = UserDto.builder()
				.firstName("Adamos")
				.lastName("Mondayas")
				.email("user_adamos@test.pl")
				.password("password_adamos")
				.build();
		HttpEntity<UserDto> request = new HttpEntity<>(requestUser, headers);
		
		ResponseEntity<UserDto> response = testRestTemplate.exchange(uri, HttpMethod.PUT, request, UserDto.class);
		
		String firstName = h2UserRepository.findById(userId).get().getFirstName();
		String email = h2UserRepository.findById(userId).get().getEmail();
		
		assertEquals("Adamos", firstName);
		assertEquals("user_adamos@test.pl", email);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldDeleteUser() {
		Long userId = 1L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/admin/users/{userId}").build(userId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
		
		assertEquals(4, h2UserRepository.findAll().size());
		assertTrue(response.getBody().contains("Deleted user"));
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void shouldNotFoundUserId() {
		Long userId = 22L;
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/admin/users/{userId}").build(userId);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<UserDto> response = testRestTemplate.exchange(uri, HttpMethod.GET, request, UserDto.class);
		
		assertEquals(404, response.getStatusCodeValue());
	}
}
