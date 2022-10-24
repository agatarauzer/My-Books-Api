package com.agatarauzer.myBooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Mail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailSenderService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(final Mail mail) {
		log.info("Starting mail preparation");
		try {
			javaMailSender.send(createMailMessage(mail));
		} catch (MailException exc) {
			log.error("Failed to process mail sending", exc.getMessage(), exc);
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
