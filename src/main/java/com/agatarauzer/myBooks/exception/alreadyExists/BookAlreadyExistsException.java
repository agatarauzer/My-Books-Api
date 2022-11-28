package com.agatarauzer.myBooks.exception.alreadyExists;

import com.agatarauzer.myBooks.exception.EntityAlreadyExistsException;

public class BookAlreadyExistsException extends EntityAlreadyExistsException {
	private static final long serialVersionUID = 1L;
	
	public BookAlreadyExistsException() {
		
	}

	public BookAlreadyExistsException(String message) {
		super(message);
	}
}
