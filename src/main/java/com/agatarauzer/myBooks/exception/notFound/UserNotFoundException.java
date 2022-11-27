package com.agatarauzer.myBooks.exception.notFound;

import com.agatarauzer.myBooks.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
