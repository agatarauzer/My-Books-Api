package com.agatarauzer.myBooks.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserFullInfoDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private LocalDate registrationDate;
	private Boolean enabled;
	private String roles;
}