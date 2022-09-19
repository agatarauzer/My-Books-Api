package com.agatarauzer.myBooks.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {
	
	private int status;
	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	
	public ErrorResponse(int status, String message, LocalDateTime timeStamp) {
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}
}
