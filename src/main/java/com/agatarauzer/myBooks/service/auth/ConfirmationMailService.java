package com.agatarauzer.myBooks.service.auth;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Mail;
import com.agatarauzer.myBooks.service.mail.MailSenderService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ConfirmationMailService {
	
	private final MailSenderService mailSenderService;
	
	public void sendConfirmationMail(String email, String token) {
		String subject = "My Books: Confirmation Link!";
		String text = "Thank you for registering! \n Please click on the link below to activate your account. \n" 
					+ "http://localhost:8080/v1/signup/confirm?token=" + token;
		
		Mail mail = new Mail(email, subject, text);
		mailSenderService.sendMail(mail);
	}
}
