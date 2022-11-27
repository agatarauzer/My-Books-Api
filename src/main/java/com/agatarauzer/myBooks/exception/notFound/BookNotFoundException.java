package com.agatarauzer.myBooks.exception.notFound;

import com.agatarauzer.myBooks.exception.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;
	
	public BookNotFoundException() {
		
	}

	public BookNotFoundException(String message) {
		super(message);
	}
}
