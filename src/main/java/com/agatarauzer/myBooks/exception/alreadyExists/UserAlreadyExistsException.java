package com.agatarauzer.myBooks.exception.alreadyExists;

import com.agatarauzer.myBooks.exception.EntityAlreadyExistsException;

public class UserAlreadyExistsException extends EntityAlreadyExistsException {
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException() {
		
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
