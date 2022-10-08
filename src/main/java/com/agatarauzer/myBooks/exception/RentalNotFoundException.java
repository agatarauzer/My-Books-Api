package com.agatarauzer.myBooks.exception;

public class RentalNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public RentalNotFoundException() {
		
	}
	public RentalNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public RentalNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RentalNotFoundException(String message) {
		super(message);
	}

	public RentalNotFoundException(Throwable cause) {
		super(cause);
	}
}
