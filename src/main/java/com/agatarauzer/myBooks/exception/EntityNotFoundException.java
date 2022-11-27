package com.agatarauzer.myBooks.exception;


public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException() {
		
	}
	
	public EntityNotFoundException(String message) {
		super(message);
	}
}
