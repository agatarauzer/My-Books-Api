package com.agatarauzer.myBooks.security.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.security.ConfirmationToken;
import com.agatarauzer.myBooks.security.repository.ConfirmationTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
	
	private final ConfirmationTokenRepository confirmationTokenRepository;
	
	public void saveConfirmationToken(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}
	
	public void deleteConfirmationToken(Long id) {
		confirmationTokenRepository.deleteById(id);
	}
	
	public Optional<ConfirmationToken> findConfirmationToken(String token) {
		return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
	}
}

