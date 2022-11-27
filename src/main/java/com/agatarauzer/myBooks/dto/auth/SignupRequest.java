package com.agatarauzer.myBooks.dto.auth;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignupRequest {
	private String firstName;
	private String lastName;
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
	private Set<String> roles;
}
