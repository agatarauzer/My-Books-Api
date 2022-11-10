package com.agatarauzer.myBooks.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;
import com.agatarauzer.myBooks.dto.UserFullInfoDto;
import com.agatarauzer.myBooks.mapper.UserMapper;
import com.agatarauzer.myBooks.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserFullInfoDto> getUsers(@RequestParam(defaultValue = "0", required = false) int page,
							@RequestParam(defaultValue = "5", required = false) int size,
							@RequestParam(defaultValue = "id", required = false) String sortBy,
							@RequestParam(defaultValue = "asc", required = false) String sortDir) {
		List<User> users = userService.findAll(page, size, sortBy, sortDir);
		return UserMapper.mapToUserFullInfoDtoList(users);
	}
	
	@GetMapping("/users/{userId}")
	public UserDto getUserById(@PathVariable Long userId) {
		User user = userService.findUserById(userId);
		return UserMapper.mapToUserDto(user);
	}
	
	@PutMapping("users/{userId}")
	public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
		User user = UserMapper.mapToUser(userDto);
		userService.updateUser(userId, user);
		return userDto;
	}
	
	@DeleteMapping("users/{userId}")
	public String deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return "Deleted user with id: " + userId;
	}
}
