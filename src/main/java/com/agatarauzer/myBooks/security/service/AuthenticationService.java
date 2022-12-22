package com.agatarauzer.myBooks.security.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.exception.alreadyExists.UserAlreadyExistsException;
import com.agatarauzer.myBooks.exception.notFound.RoleNotFoundException;
import com.agatarauzer.myBooks.security.ConfirmationToken;
import com.agatarauzer.myBooks.security.ERole;
import com.agatarauzer.myBooks.security.Role;
import com.agatarauzer.myBooks.security.dto.JwtResponse;
import com.agatarauzer.myBooks.security.dto.LoginRequest;
import com.agatarauzer.myBooks.security.dto.MessageResponse;
import com.agatarauzer.myBooks.security.dto.SignupRequest;
import com.agatarauzer.myBooks.security.jwt.JwtUtils;
import com.agatarauzer.myBooks.security.repository.RoleRepository;
import com.agatarauzer.myBooks.user.User;
import com.agatarauzer.myBooks.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;
	private final ConfirmationTokenService confirmationTokenService;
	private final ConfirmationMailService confirmationMailService;
	
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return JwtResponse.builder()
				.token(jwtToken)
				.id(userDetails.getId())
				.username(userDetails.getUsername())
				.email(userDetails.getEmail())
				.roles(roles)
				.build();	
	}
	
	public MessageResponse registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new UserAlreadyExistsException("Error: Username is already taken!");
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistsException("Error: Email is already in use!");
		}
		
		User user = User.builder()
				.firstName(signUpRequest.getFirstName())
				.lastName(signUpRequest.getLastName())
				.username(signUpRequest.getUsername())
				.email(signUpRequest.getEmail())
				.password(encoder.encode(signUpRequest.getPassword()))
				.build();
		
		Set<Role> roles = setupUserRoles(signUpRequest.getRoles());
		user.setRoles(roles);
		user.setRegistrationDate(LocalDate.now());
		userRepository.save(user);
		log.info("User is saved in DB (but not confirmed yet)");
		
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		log.info("Confirmation token saved in db");
		
		confirmationMailService.sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
		log.info("Confirmation mail has been send to user");
		
		return new MessageResponse("User registred sucessfully!");
	}
	
	public void confirmUser(ConfirmationToken confirmationToken) {
		User user = confirmationToken.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		log.info("User is confirmed and enabled");
		
		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
		log.info("Confirmation token was deleted");
	}
	
	private Set<Role> setupUserRoles(Set<String> givenRoles) {
		Set<Role> roles = new HashSet<>();
		if (givenRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER_LIMITED)
				.orElseThrow(() -> new RoleNotFoundException("Error: Role: " + ERole.ROLE_USER_LIMITED.toString() + " is not found"));
			roles.add(userRole);
		}
		else {
			givenRoles.forEach(role -> {
				switch (role) {
				case "userPaid":
					Role userPaidRole = roleRepository.findByName(ERole.ROLE_USER_PAID)
						.orElseThrow(() -> new RoleNotFoundException("Error: Role: " + ERole.ROLE_USER_PAID.toString() + " is not found"));	
					roles.add(userPaidRole);
					break;
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RoleNotFoundException("Error: Role: " + ERole.ROLE_ADMIN.toString() + " is not found"));
					roles.add(adminRole);
					break;
				default: 
					Role userRole = roleRepository.findByName(ERole.ROLE_USER_LIMITED)
						.orElseThrow(() -> new RoleNotFoundException("Error: Role: " + ERole.ROLE_USER_LIMITED.toString() + " is not found"));
					roles.add(userRole);
					break;
				}
			});
		}
		return roles;
	}
}
