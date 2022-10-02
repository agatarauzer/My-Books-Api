package com.agatarauzer.myBooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	public void shouldfindAllUsers() {
		List<User> userList = List.of(
				new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password"),
				new User(2L, "Alicja", "Maj", "ala.maj@gmail.com", "agamaj", "aga_maj_password"));
		
		when(userRepository.findAll()).thenReturn(userList);
		
		List<User> users = userService.findAll();
		
		assertEquals(2, users.size());
		assertEquals(1L, users.get(0).getId());
		assertEquals("Tomasz", users.get(0).getFirstName());
		assertEquals("Malinowski", users.get(0).getLastName());
		assertEquals("ala.maj@gmail.com", users.get(1).getEmail());
		assertEquals("agamaj", users.get(1).getLogin());
		assertEquals("aga_maj_password", users.get(1).getPassword());
	}
	
	@Test
	public void sholudFindUserById() {
		Long id = 1L;
		Optional<User> user = Optional.of(new User(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password"));
		
		when(userRepository.findById(id)).thenReturn(user);
		
		User foundedUser = userService.findUserById(id);
		
		assertEquals("Tomasz", foundedUser.getFirstName());
		assertEquals("Malinowski", foundedUser.getLastName());
		assertEquals("tomasz.malinowski@gmail.com", foundedUser.getEmail());
		assertEquals("tommal", foundedUser.getLogin());
		assertEquals("tom_mal_password", foundedUser.getPassword());
	}
	
	@Test
	public void sholudSaveUser() {
		User user = new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		
		when(userRepository.save(user)).thenReturn(user);
		
		User savedUser = userService.saveUser(user);
		
		assertEquals("Tomasz", savedUser.getFirstName());
		assertEquals("Malinowski", savedUser.getLastName());
		assertEquals("tomasz.malinowski@gmail.com", savedUser.getEmail());
		assertEquals("tommal", savedUser.getLogin());
		assertEquals("tom_mal_password", savedUser.getPassword());
	}
	
	@Test
	public void sholudUpdateUser() {
		Long id = 1L;
		Optional<User> userOptional = Optional.of(new User(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password"));
		User userUpdated = new User(id, "Tomek", "Malik", "tomasz.malik@gmail.com", "tommalik", "to_password");
		
		when(userRepository.findById(id)).thenReturn(userOptional);
		when(userRepository.save(any(User.class))).thenReturn(userUpdated);
		
		User userAfterUpdate = userService.updateUser(id, userUpdated);
		
		assertEquals(userUpdated.getFirstName(), userAfterUpdate.getFirstName());
		assertEquals(userUpdated.getLastName(), userAfterUpdate.getLastName());
		assertEquals(userUpdated.getEmail(), userAfterUpdate.getEmail());
		assertEquals(userUpdated.getLogin(), userAfterUpdate.getLogin());
		assertEquals(userUpdated.getPassword(), userAfterUpdate.getPassword());
	}
	
	@Test
	public void shouldDeleteUser() {
		Long id = 1L;
		doNothing().when(userRepository).deleteById(id);
		userService.deleteUser(id);
		
		verify(userRepository, times(1)).deleteById(id);
	}
	
	@Test
	public void shouldThrowException_findUserById() {
		Long id = 1L;
		doThrow(new UserNotFoundException()).when(userRepository).findById(id);
		
		assertThrows(UserNotFoundException.class, ()-> userService.findUserById(id));
		verify(userRepository, times(1)).findById(id);
	}
	
	@Test
	public void shouldThrowException_updateUser() {
		Long id = 1L;
		doThrow(new UserNotFoundException()).when(userRepository).findById(id);
		
		assertThrows(UserNotFoundException.class, ()-> userService.updateUser(id, new User()));
		verify(userRepository, times(1)).findById(id);
	}
	
	@Test
	public void shouldThrowException_deleteUser() {
		Long id = 1L;
		doThrow(new UserNotFoundException()).when(userRepository).deleteById(id);
		
		assertThrows(UserNotFoundException.class, ()-> userService.deleteUser(id));
		verify(userRepository, times(1)).deleteById(id);
	}
}


