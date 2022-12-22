package com.agatarauzer.myBooks.utils.emailSender;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {

	private final JavaMailSender javaMailSender;
	
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
