package com.agatarauzer.myBooks.service;

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

import com.agatarauzer.myBooks.domain.ConfirmationToken;
import com.agatarauzer.myBooks.domain.Mail;
import com.agatarauzer.myBooks.domain.Role;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.domain.enums.ERole;
import com.agatarauzer.myBooks.dto.singUpIn.JwtResponse;
import com.agatarauzer.myBooks.dto.singUpIn.LoginRequest;
import com.agatarauzer.myBooks.dto.singUpIn.MessageResponse;
import com.agatarauzer.myBooks.dto.singUpIn.SignupRequest;
import com.agatarauzer.myBooks.repository.RoleRepository;
import com.agatarauzer.myBooks.repository.UserRepository;
import com.agatarauzer.myBooks.security.jwt.JwtUtils;
import com.agatarauzer.myBooks.security.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;
	private final ConfirmationTokenService confirmationTokenService;
	private final MailSenderService mailSenderService;
	
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
	
	public  MessageResponse registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new MessageResponse("Error: Username is already taken!");
		}
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new MessageResponse("Error: Email is already in use!");
		}
		
		User user = User.builder()
				.firstName(signUpRequest.getFirstName())
				.lastName(signUpRequest.getLastName())
				.username(signUpRequest.getUsername())
				.email(signUpRequest.getEmail())
				.password(encoder.encode(signUpRequest.getPassword()))
				.build();
		
		Set<Role> roles = setupUserRoles(signUpRequest.getRole());
		user.setRoles(roles);
		user.setRegistrationDate(LocalDate.now());
		userRepository.save(user);
		log.info("User is saved in DB (but not confirmed yet)");
		
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
		log.info("Confirmation mail has been send to user");
		
		return new MessageResponse("User registred sucessfully!");
	}
	
	private Set<Role> setupUserRoles(Set<String> givenRoles) {
		Set<Role> roles = new HashSet<>();
		if (givenRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER_LIMITED)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(userRole);
		}
		else {
			givenRoles.forEach(role -> {
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
		return roles;
	}
	
	private void sendConfirmationMail(String email, String token) {
		String subject = "My Books: Confirmation Link!";
		String text = "Thank you for registering! \n Please click on the link below to activate your account. \n" 
					+ "http://localhost:8080/v1/auth/signup/confirm?token=" + token;
		
		Mail mail = new Mail(email, subject, text);
		mailSenderService.sendMail(mail);
	}
}
