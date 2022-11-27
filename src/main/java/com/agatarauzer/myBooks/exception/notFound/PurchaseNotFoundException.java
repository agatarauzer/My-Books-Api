package com.agatarauzer.myBooks.exception.notFound;

import com.agatarauzer.myBooks.exception.EntityNotFoundException;

public class PurchaseNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;
	
	public PurchaseNotFoundException() {
		
	}

	public PurchaseNotFoundException(String message) {
		super(message);
	}
}
