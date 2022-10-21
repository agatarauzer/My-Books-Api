package com.agatarauzer.myBooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.payload.request.LoginRequest;
import com.agatarauzer.myBooks.payload.request.SignupRequest;
import com.agatarauzer.myBooks.payload.response.JwtResponse;
import com.agatarauzer.myBooks.payload.response.MessageResponse;
import com.agatarauzer.myBooks.service.AuthService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
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
}
