package com.agatarauzer.myBooks.dto;

import java.util.List;

import com.agatarauzer.myBooks.domain.Book;

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

	private List<Book> books;
	
	public UserDto(Long id, String firstName, String lastName, String email, 
			String login, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.login = login;
		this.password = password;
	}
}
