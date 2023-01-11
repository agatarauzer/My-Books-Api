package com.agatarauzer.myBooks.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
	private String receiverMail;
	private String subject;
	private String message;
}
