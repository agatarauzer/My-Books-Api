package com.agatarauzer.myBooks.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.ConfirmationToken;
import com.agatarauzer.myBooks.domain.ERole;
import com.agatarauzer.myBooks.domain.Role;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.payload.request.LoginRequest;
import com.agatarauzer.myBooks.payload.request.SignupRequest;
import com.agatarauzer.myBooks.payload.response.JwtResponse;
import com.agatarauzer.myBooks.payload.response.MessageResponse;
import com.agatarauzer.myBooks.repository.RoleRepository;
import com.agatarauzer.myBooks.repository.UserRepository;
import com.agatarauzer.myBooks.security.jwt.JwtUtils;
import com.agatarauzer.myBooks.security.service.UserDetailsImpl;
import com.agatarauzer.myBooks.service.ConfirmationTokenService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt,
								userDetails.getId(),
								userDetails.getUsername(),
								userDetails.getEmail(),
								roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		
		//create new account
		User user = new User(signUpRequest.getFirstName(),
				signUpRequest.getLastName(),
				signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER_LIMITED)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(userRole);
		}
		else {
			strRoles.forEach(role -> {
				switch (role) {
				case "userPaid":
					Role userPaidRole = roleRepository.findByName(ERole.ROLE_USER_PAID)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(userPaidRole);
					
					break;
				
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(adminRole);
					
					break;
				
				default: 
					Role userRole = roleRepository.findByName(ERole.ROLE_USER_LIMITED)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(userRole);
				
					break;
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return ResponseEntity.ok(new MessageResponse("User registred sucessfully!"));
	}
}