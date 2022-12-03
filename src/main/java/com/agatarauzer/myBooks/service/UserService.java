package com.agatarauzer.myBooks.service;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.exception.notFound.UserNotFoundException;
import com.agatarauzer.myBooks.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;

	public List<User> findAll(int page, int size, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return IterableUtils.toList(userRepository.findAll(pageable));
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
		userUpdated.setPassword(user.getPassword());
		log.info("User with id: " + userId + " was updated");
		return saveUser(userUpdated);
	}
	
	public void deleteUser(Long userId) {
		userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		userRepository.deleteById(userId);
		log.info("User with id: " + userId + " was deleted");
	}	
}

