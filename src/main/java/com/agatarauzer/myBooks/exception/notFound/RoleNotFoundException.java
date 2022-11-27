package com.agatarauzer.myBooks.exception.notFound;

import com.agatarauzer.myBooks.exception.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;
	
	public RoleNotFoundException() {
		
	}
	
	public RoleNotFoundException(String message) {
		super(message);
	}
}
