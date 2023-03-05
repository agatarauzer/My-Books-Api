package com.agatarauzer.myBooks.authentication.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Builder
@Setter
@Getter
public class LoginRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
