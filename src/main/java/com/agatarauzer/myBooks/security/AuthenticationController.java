package com.agatarauzer.myBooks.security;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.security.dto.JwtResponse;
import com.agatarauzer.myBooks.security.dto.LoginRequest;
import com.agatarauzer.myBooks.security.dto.MessageResponse;
import com.agatarauzer.myBooks.security.dto.SignupRequest;
import com.agatarauzer.myBooks.security.service.AuthenticationService;
import com.agatarauzer.myBooks.security.service.ConfirmationTokenService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authService;
	private final ConfirmationTokenService confirmationTokenService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		MessageResponse messageResponse = authService.registerUser(signUpRequest);
		return ResponseEntity.ok(messageResponse);
	}
	
	@GetMapping("/signup/confirm")
	public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
		Optional<ConfirmationToken> optConfirmationToken = confirmationTokenService.findConfirmationToken(token);
		optConfirmationToken.ifPresent(authService::confirmUser);
		return ResponseEntity.ok("Thank you for confirmation");
	}
}
