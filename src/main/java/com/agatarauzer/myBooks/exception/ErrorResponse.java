package com.agatarauzer.myBooks.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {
	
	private int status;
	private String message;
	private long timeStamp;
	
	public ErrorResponse(int status, String message, long timeStamp) {
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}
}
