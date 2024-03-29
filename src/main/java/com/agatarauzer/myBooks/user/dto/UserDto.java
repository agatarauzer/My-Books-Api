package com.agatarauzer.myBooks.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
