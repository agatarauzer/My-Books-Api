package com.agatarauzer.myBooks.authentication;

import com.agatarauzer.myBooks.authentication.confirmationToken.ConfirmationToken;
import com.agatarauzer.myBooks.authentication.confirmationToken.ConfirmationTokenService;
import com.agatarauzer.myBooks.authentication.payload.JwtResponse;
import com.agatarauzer.myBooks.authentication.payload.LoginRequest;
import com.agatarauzer.myBooks.authentication.payload.MessageResponse;
import com.agatarauzer.myBooks.authentication.payload.SignupRequest;
import com.agatarauzer.myBooks.authentication.role.ERole;
import com.agatarauzer.myBooks.authentication.role.Role;
import com.agatarauzer.myBooks.authentication.role.RoleRepository;
import com.agatarauzer.myBooks.exception.alreadyExists.UserAlreadyExistsException;
import com.agatarauzer.myBooks.exception.notFound.RoleNotFoundException;
import com.agatarauzer.myBooks.mail.confirmationMail.ConfirmationMailService;
import com.agatarauzer.myBooks.security.jwt.JwtUtils;
import com.agatarauzer.myBooks.security.service.UserDetailsImpl;
import com.agatarauzer.myBooks.user.User;
import com.agatarauzer.myBooks.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;
	private final ConfirmationTokenService confirmationTokenService;
	private final ConfirmationMailService confirmationMailService;
	private final UserService userService;
	
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
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
		if (userService.checkIfUsernameExists(signUpRequest.getUsername())) {
			throw new UserAlreadyExistsException("Error: Username is already taken!");
		}
		if (userService.checkIfEmailExists(signUpRequest.getEmail())) {
			throw new UserAlreadyExistsException("Error: Email is already in use!");
		}
		
		User user = User.builder()
				.username(signUpRequest.getUsername())
				.email(signUpRequest.getEmail())
				.password(encoder.encode(signUpRequest.getPassword()))
				.build();
		
		Set<Role> roles = setupUserRoles(signUpRequest.getRoles());
		user.setRoles(roles);
		user.setRegistrationDate(LocalDate.now());
		userService.createUser(user);
		log.info("New user is saved in DB (but not confirmed yet)");
		createConfirmationTokenAndSendToUser(user);
		return new MessageResponse("User registered: waiting for confirmation!");
	}

	public void confirmUser(ConfirmationToken confirmationToken) {
		User user = confirmationToken.getUser();
		userService.enableUser(user);
		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
		log.info("Confirmation token was deleted");
	}

	private void createConfirmationTokenAndSendToUser(User user) {
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		log.info("Confirmation token saved in db");
		confirmationMailService.sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
		log.info("Confirmation mail has been send to user");
	}
	
	private Set<Role> setupUserRoles(Set<String> givenRoles) {
		Set<Role> roles = new HashSet<>();
		if (givenRoles == null) {
			Role userRole = roleRepository.findByName(ERole.USER)
				.orElseThrow(() -> new RoleNotFoundException("Error: Role: " + ERole.USER + " is not found"));
			roles.add(userRole);
		}
		else {
			givenRoles.forEach(role -> {
				switch (role) {
					case "user":
						Role userRole = roleRepository.findByName(ERole.USER)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role: " + ERole.USER + " is not found"));
						roles.add(userRole);
						break;

					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ADMIN)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role: " + ERole.ADMIN + " is not found"));
						roles.add(adminRole);
						break;
				}
			});
		}
		return roles;
	}
}