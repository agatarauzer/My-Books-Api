package com.agatarauzer.myBooks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
