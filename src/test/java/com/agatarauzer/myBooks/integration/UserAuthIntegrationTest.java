package com.agatarauzer.myBooks.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.agatarauzer.myBooks.dto.auth.JwtResponse;
import com.agatarauzer.myBooks.dto.auth.LoginRequest;
import com.agatarauzer.myBooks.dto.auth.MessageResponse;
import com.agatarauzer.myBooks.dto.auth.SignupRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAuthIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String baseUrl = "http://localhost";
	
	private static RestTemplate restTemplate;
	
	@Autowired
	private TestH2Repository h2Repository;
	
	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/v1");
	}
	
	@Test
	public void shouldRegisterUser() {
		baseUrl = baseUrl.concat("/signup");
		SignupRequest signUpRequest = SignupRequest.builder()
							.username("test_user1")
							.email("user1@test.pl")
							.password("user1_password")
							.build();
		
		MessageResponse messageResponse = restTemplate.postForObject(baseUrl, signUpRequest, MessageResponse.class);
					
		assertEquals("User registred sucessfully!", messageResponse.getMessage());
		assertEquals(1, h2Repository.findAll().size());
	}
	
	@Test
	public void shouldSignInUser() {
		baseUrl = baseUrl.concat("/signin");
		LoginRequest loginRequest = LoginRequest.builder()
				.username("test_user1")
				.password("user1_password")
				.build();
		
		JwtResponse jwtResponse = restTemplate.postForObject(baseUrl, loginRequest, JwtResponse.class);
		
		assertEquals("test_user1", jwtResponse.getUsername());
		
	}
}

