package com.agatarauzer.myBooks.exception.notFound;

import com.agatarauzer.myBooks.exception.EntityNotFoundException;

public class RentalNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;
	
	public RentalNotFoundException() {
		
	}
	
	public RentalNotFoundException(String message) {
		super(message);
	}
}
