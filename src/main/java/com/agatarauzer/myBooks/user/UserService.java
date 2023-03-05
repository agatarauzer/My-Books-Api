package com.agatarauzer.myBooks.user;

import com.agatarauzer.myBooks.exception.notFound.UserNotFoundException;
import com.agatarauzer.myBooks.owner.Owner;
import com.agatarauzer.myBooks.owner.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public User createUser(User user) {
		Owner owner = new Owner();
		user.setOwner(owner);
		userRepository.save(user);
		return user;
	}

	public User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
	}

	public User saveUser(User user) {
		userRepository.save(user);
		return user;
	}

	public void enableUser(User user) {
		user.setEnabled(true);
		saveUser(user);
		log.info("User with id: " + user.getId() + " was enabled and saved in db");
	}

	public boolean checkIfUsernameExists(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean checkIfEmailExists(String email) {
		return userRepository.existsByEmail(email);
	}

	public User updateUser(User user) {
		userRepository.findById(user.getId())
			.orElseThrow(() -> new UserNotFoundException("User id not found: " + user.getId()));
		saveUser(user);
		log.info("User with id: " + user.getId() + " was updated");
		return user;
	}
	
	public void deleteUser(Long userId) {
		userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		userRepository.deleteById(userId);
		log.info("User with id: " + userId + " was deleted");
	}
}

