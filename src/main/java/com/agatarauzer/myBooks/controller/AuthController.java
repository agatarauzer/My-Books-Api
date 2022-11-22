package com.agatarauzer.myBooks.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.ConfirmationToken;
import com.agatarauzer.myBooks.dto.singUpIn.JwtResponse;
import com.agatarauzer.myBooks.dto.singUpIn.LoginRequest;
import com.agatarauzer.myBooks.dto.singUpIn.MessageResponse;
import com.agatarauzer.myBooks.dto.singUpIn.SignupRequest;
import com.agatarauzer.myBooks.service.AuthService;
import com.agatarauzer.myBooks.service.ConfirmationTokenService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	private final ConfirmationTokenService confirmationTokenService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		MessageResponse messageResponse = authService.registerUser(signUpRequest);
		if (messageResponse.getMessage().contains("Error")) {
			return ResponseEntity
					.badRequest()
					.body(messageResponse);
		}
		return ResponseEntity.ok(messageResponse);
	}
	
	@GetMapping("/signup/confirm")
	public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
		Optional<ConfirmationToken> optConfirmationToken = confirmationTokenService.findConfirmationToken(token);
		optConfirmationToken.ifPresent(authService::confirmUser);
		return ResponseEntity.ok("Thank you for confirmation");
	}
}
