package com.agatarauzer.myBooks.exception;

public class PurchaseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PurchaseNotFoundException() {
		
	}

	public PurchaseNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PurchaseNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PurchaseNotFoundException(String message) {
		super(message);
	}

	public PurchaseNotFoundException(Throwable cause) {
		super(cause);
	}
}
