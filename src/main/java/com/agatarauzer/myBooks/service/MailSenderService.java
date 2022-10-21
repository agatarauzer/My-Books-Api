package com.agatarauzer.myBooks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Mail;

@Service
public class MailSenderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(final Mail mail) {
		LOGGER.info("Starting mail preparation");
		try {
			javaMailSender.send(createMailMessage(mail));
			LOGGER.info("Confirmation mail has been sent");
		} catch (MailException exc) {
			LOGGER.error("Failed to process confirmation mail sending", exc.getMessage(), exc);
		}
	}
	
	private SimpleMailMessage createMailMessage(final Mail mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail.getReceiverMail());
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setText(mail.getMessage());
		return mailMessage;
	}
}
