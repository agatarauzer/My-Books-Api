package com.agatarauzer.myBooks.security.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LoginRequest {
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}