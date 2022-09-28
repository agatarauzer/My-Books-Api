package com.agatarauzer.myBooks.exception;

public class ReadingNotFoundException extends RuntimeException {
	
	
private static final long serialVersionUID = 1L;
	
	public ReadingNotFoundException() {
		
	}

	public ReadingNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ReadingNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ReadingNotFoundException(String message) {
		super(message);
	}

	public ReadingNotFoundException(Throwable cause) {
		super(cause);
	}
}
