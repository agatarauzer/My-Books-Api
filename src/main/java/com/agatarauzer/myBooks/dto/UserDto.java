package com.agatarauzer.myBooks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
	
	private Long id;
	
	private String firstName;

	private String lastName;
	
	private String email;
	
	private String login;
	
	private String password;
}
