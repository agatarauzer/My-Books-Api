package com.agatarauzer.myBooks.service;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.exception.ReadingNotFoundException;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
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
	
	public User updateUser(Long userId, User user) {
		User userUpdated = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		userUpdated.setFirstName(user.getFirstName());
		userUpdated.setLastName(user.getLastName());
		userUpdated.setEmail(user.getEmail());
		userUpdated.setLogin(user.getLogin());
		userUpdated.setPassword(user.getPassword());
		return saveUser(userUpdated);
	}
	
	public void deleteUser(Long userId) {
		try {
			userRepository.deleteById(userId);
		} catch (DataAccessException exc) {
			throw new ReadingNotFoundException("Reading id not found: " + userId);
		}
	}		
}

