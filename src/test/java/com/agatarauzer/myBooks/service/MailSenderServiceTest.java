package com.agatarauzer.myBooks.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.agatarauzer.myBooks.domain.Mail;

@ExtendWith(MockitoExtension.class)
public class MailSenderServiceTest {
	
	@InjectMocks
	private MailSenderService mailSenderService;
	
	@Mock
	private JavaMailSender javaMailSender;
	
	@Test
	public void shouldSendEmail() {
		Mail mail = new Mail("test@test.com", "Test", "Test message");
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail.getReceiverMail());
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setText(mail.getMessage());
		
		mailSenderService.sendMail(mail);
		
		verify(javaMailSender, times(1)).send(mailMessage);
	}
}
