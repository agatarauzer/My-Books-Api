package com.agatarauzer.myBooks.exception;

public class BookAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public BookAlreadyExistsException() {
		
	}

	public BookAlreadyExistsException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BookAlreadyExistsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BookAlreadyExistsException(String message) {
		super(message);
	}

	public BookAlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
