package com.agatarauzer.myBooks.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserForAdminDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String roles;
}