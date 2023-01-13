package com.agatarauzer.myBooks.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.user.User;
import com.agatarauzer.myBooks.user.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticatedUserService {
	
	private final UserRepository userRepository;
	
	public boolean hasId(Long userId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username).get();
		return user.getId().equals(userId);
	}
}
