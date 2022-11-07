package com.agatarauzer.myBooks.service;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.ConfirmationToken;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final ConfirmationTokenService confirmationTokenService;
	
	public List<User> findAll() {
		return IterableUtils.toList(userRepository.findAll());
	}
	
	public User findUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public void confirmUser(ConfirmationToken confirmationToken) {
		User user = confirmationToken.getUser();
		user.setEnabled(true);
		saveUser(user);
		log.info("User is confirmed and enabled");
		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
	}
	
	public User updateUser(Long userId, User user) {
		User userUpdated = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		userUpdated.setFirstName(user.getFirstName());
		userUpdated.setLastName(user.getLastName());
		userUpdated.setEmail(user.getEmail());
		userUpdated.setUsername(user.getUsername());
		userUpdated.setPassword(user.getPassword());
		saveUser(userUpdated);
		log.info("User is updated");
		return userUpdated;
	}
	
	public void deleteUser(Long userId) {
		try {
			userRepository.deleteById(userId);
		} catch (DataAccessException exc) {
			throw new UserNotFoundException("User id not found: " + userId);
		}
	}		
}

