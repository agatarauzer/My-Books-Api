package com.agatarauzer.myBooks.dto.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
	@Builder.Default
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
}
