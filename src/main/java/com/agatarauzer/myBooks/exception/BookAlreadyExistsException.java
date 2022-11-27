package com.agatarauzer.myBooks.exception;

public class BookAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public BookAlreadyExistsException() {
		
	}

	public BookAlreadyExistsException(String message) {
		super(message);
	}
}
