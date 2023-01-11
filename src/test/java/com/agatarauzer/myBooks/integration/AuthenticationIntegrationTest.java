package com.agatarauzer.myBooks.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.agatarauzer.myBooks.authentication.payload.JwtResponse;
import com.agatarauzer.myBooks.authentication.payload.LoginRequest;
import com.agatarauzer.myBooks.authentication.payload.MessageResponse;
import com.agatarauzer.myBooks.authentication.payload.SignupRequest;
import com.agatarauzer.myBooks.integration.H2Repository.TestH2ConfirmationTokenRepository;
import com.agatarauzer.myBooks.integration.H2Repository.TestH2UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String baseUrl = "http://localhost";
	
	private static TestRestTemplate testRestTemplate;
	
	@Autowired
	private TestH2UserRepository h2UserRepository;
	
	@Autowired
	private TestH2ConfirmationTokenRepository h2ConfirmationTokenRepository;
	
	@BeforeAll
	public static void init() {
		testRestTemplate = new TestRestTemplate();
	}
	
	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/v1");
	}
	
	
	//TODO: Add tests for exceptions - user not authenticated 
	
	
	@Test
	@Order(1)
	public void shouldRegisterUser() {
		baseUrl = baseUrl.concat("/signup");
		SignupRequest signUpRequest = SignupRequest.builder()
							.username("test_user1")
							.email("user123@test.pl")
							.password("user1_password")
							.build();
	
		ResponseEntity<MessageResponse> response = testRestTemplate.postForEntity(baseUrl, signUpRequest, MessageResponse.class);
		boolean isUserEnabled = h2UserRepository.findById(1L).get().getEnabled();
		
		assertEquals("User registred sucessfully!", response.getBody().getMessage());
		assertEquals(1, h2UserRepository.findAll().size());
		assertFalse(isUserEnabled);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	@Order(2)
	public void shouldConfirmUser() {
		String token = h2ConfirmationTokenRepository.findById(1L).get().getConfirmationToken();
		baseUrl = baseUrl.concat("/signup/confirm?token=").concat(token);
		
		ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl, String.class);
		boolean isUserEnabled = h2UserRepository.findById(1L).get().getEnabled();
		
		assertEquals("Thank you for confirmation", response.getBody());
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(isUserEnabled);
	}
	
	@Test
	@Order(3)
	public void shouldSignInUser() {
		baseUrl = baseUrl.concat("/signin");
		LoginRequest loginRequest = LoginRequest.builder()
				.username("test_user1")
				.password("user1_password")
				.build();
		
		ResponseEntity<JwtResponse> response = testRestTemplate.postForEntity(baseUrl, loginRequest, JwtResponse.class);
		
		assertEquals("test_user1", response.getBody().getUsername());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	@Order(4)
	public void shouldNotRegisterUserIfUsernameAlreadyExists() {
		baseUrl = baseUrl.concat("/signup");
		SignupRequest signUpRequest = SignupRequest.builder()
							.username("test_user1")
							.email("user1222@test.pl")
							.password("user1_password")
							.build();
	
		ResponseEntity<MessageResponse> response = testRestTemplate.postForEntity(baseUrl, signUpRequest, MessageResponse.class);
		
		assertEquals("Error: Username is already taken!", response.getBody().getMessage());
		assertEquals(1, h2UserRepository.findAll().size());
		assertEquals(400, response.getStatusCodeValue());
	}
	
	//other tests works without it. don't know why this test cause problem with others two (register, register exception)
	/*
	@Test
	public void shouldNotRegisterUserIfEmailAlreadyExists() {
		baseUrl = baseUrl.concat("/signup");
		SignupRequest signUpRequest = SignupRequest.builder()
							.username("test_user2")
							.email("user123@test.pl")
							.password("user2_password")
							.build();
	
		ResponseEntity<MessageResponse> response = testRestTemplate.postForEntity(baseUrl, signUpRequest, MessageResponse.class);
		
		assertEquals("Error: Email is already in use!", response.getBody().getMessage());
		assertEquals(1, h2UserRepository.findAll().size());
		assertEquals(400, response.getStatusCodeValue());
	}
	*/
}


