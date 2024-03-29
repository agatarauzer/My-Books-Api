package com.agatarauzer.myBooks.unit.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.agatarauzer.myBooks.authentication.AuthenticationController;
import com.agatarauzer.myBooks.authentication.AuthenticationService;
import com.agatarauzer.myBooks.authentication.confirmationToken.ConfirmationTokenService;
import com.agatarauzer.myBooks.authentication.payload.JwtResponse;
import com.agatarauzer.myBooks.authentication.payload.LoginRequest;
import com.agatarauzer.myBooks.authentication.payload.MessageResponse;
import com.agatarauzer.myBooks.authentication.payload.SignupRequest;
import com.google.gson.Gson;


@SpringJUnitWebConfig
@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthenticationService authService;
	
	@MockBean
	private ConfirmationTokenService confirmationTokenService;
	
	@Test
	public void shouldSignIn() throws Exception {
		JwtResponse response = JwtResponse.builder()
				.token("kdk4kkkdkdkkf")
				.id(1L)
				.username("test_username")
				.email("test_email@test.pl")
				.roles((List.of("USER_PAID")))
				.build();
		LoginRequest request = LoginRequest.builder()
				.username("test_username")
				.password("12345")
				.build();
		
		when(authService.authenticateUser(request)).thenReturn(response);

		Gson gson = new Gson();
		String jsonRequest = gson.toJson(request);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonRequest))
				.andExpect(status().is(200));		
	}
	
	
	// not working, giving status 400 
	@Test
	public void shouldSignUp() throws Exception {
		SignupRequest request = SignupRequest.builder()
			.firstName("test_firstName")
			.lastName("test_lastName")
			.username("test_username")
			.email("test_email@test.pl")
			.password("test_password")
			.roles((Set.of("USER_PAID")))
			.build();
		MessageResponse messageSucess = new MessageResponse("User registred sucessfully!");
		
		when(authService.registerUser(request)).thenReturn(messageSucess);
		
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(request);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonRequest))
				.andExpect(status().is(200));	
	}
}

