package com.agatarauzer.myBooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findUserById(Long userId) {
		return userRepository.getReferenceById(userId);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public User updateUser(Long userId, User user) {
		
		User userUpdated = userRepository.getReferenceById(userId);
		
		userUpdated.setFirstName(user.getFirstName());
		userUpdated.setLastName(user.getLastName());
		userUpdated.setEmail(user.getEmail());
		userUpdated.setLogin(user.getLogin());
		userUpdated.setPassword(user.getPassword());
		
		return saveUser(userUpdated);
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}		
}

