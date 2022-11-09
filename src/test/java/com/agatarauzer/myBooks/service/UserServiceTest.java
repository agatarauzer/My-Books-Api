package com.agatarauzer.myBooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agatarauzer.myBooks.domain.Role;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.domain.enums.ERole;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	private Long userId;
	private User user;
	
	@BeforeEach
	public void prepareTestData() {
		userId = 1L;
		user = User.builder()
				.id(1L)
				.firstName("Tomasz")
				.lastName("Malinowski")
				.email("tomasz.malinowski@gmail.com")
				.username("tommal")
				.password("tom_mal_password")
				.roles(Set.of(new Role(ERole.ROLE_ADMIN)))
				.build();
	}

	@Test
	public void shouldfindAllUsers() {
		List<User> userList = List.of(user,
							User.builder()
							.id(2L)
							.firstName("Alicja")
							.lastName("Maj")
							.email("ala.maj@gmail.com")
							.username("agamaj")
							.password("aga_maj_password")
							.roles(Set.of(new Role(ERole.ROLE_USER_PAID)))
							.build());
		when(userRepository.findAll()).thenReturn(userList);
		
		List<User> users = userService.findAll();
		
		assertEquals(2, users.size());
		assertEquals(userList.get(0), users.get(0)); 
	}
	
	@Test
	public void sholudFindUserById() {
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		
		User foundedUser = userService.findUserById(userId);
		
		assertEquals(user, foundedUser);
	}
	
	@Test
	public void sholudSaveUser() {
		when(userRepository.save(user)).thenReturn(user);
		
		User savedUser = userService.saveUser(user);
		
		assertEquals(user, savedUser);
	}
	
	@Test
	public void sholudUpdateUser() {
		User userUpdated =  User.builder()
				.id(userId)
				.firstName("Tomek")
				.lastName("Malik")
				.email("tomasz.malik@gmail.com")
				.username("tommalik")
				.password("to_password")
				.roles(Set.of(new Role(ERole.ROLE_USER_PAID)))
				.build();
		
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(userUpdated);
		
		User userAfterUpdate = userService.updateUser(userId, userUpdated);
		
		assertEquals(userUpdated.getEmail(), userAfterUpdate.getEmail());
		assertEquals(userUpdated.getLastName(), userAfterUpdate.getLastName());
	}
	
	@Test
	public void shouldDeleteUser() {
		userService.deleteUser(userId);
		
		verify(userRepository, times(1)).deleteById(userId);
	}
	
	@Test
	public void shouldThrowException_findUserById() {
		doThrow(new UserNotFoundException()).when(userRepository).findById(userId);
		
		assertThrows(UserNotFoundException.class, ()-> userService.findUserById(userId));
		verify(userRepository, times(1)).findById(userId);
	}
	
	@Test
	public void shouldThrowException_updateUser() {
		doThrow(new UserNotFoundException()).when(userRepository).findById(userId);
		
		assertThrows(UserNotFoundException.class, ()-> userService.updateUser(userId, new User()));
		verify(userRepository, times(1)).findById(userId);
	}
	
	@Test
	public void shouldThrowException_deleteUser() {
		doThrow(new UserNotFoundException()).when(userRepository).deleteById(userId);
		
		assertThrows(UserNotFoundException.class, ()-> userService.deleteUser(userId));
		verify(userRepository, times(1)).deleteById(userId);
	}
}


