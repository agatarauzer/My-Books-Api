package com.agatarauzer.myBooks.exception.notFound;

import com.agatarauzer.myBooks.exception.EntityNotFoundException;

public class ReadingNotFoundException extends EntityNotFoundException {
private static final long serialVersionUID = 1L;
	
	public ReadingNotFoundException() {
		
	}

	public ReadingNotFoundException(String message) {
		super(message);
	}
}
