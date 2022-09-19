package com.agatarauzer.myBooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.mapper.UserMapper;
import com.agatarauzer.myBooks.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	
	@PostMapping
	public UserDto addUser(@RequestBody UserDto userDto) {
		User user = userMapper.mapToUser(userDto);
		user.setId((long)0);
		userService.saveUser(user);
		return userDto;
	}
	
	@GetMapping
	public List<UserDto> getUsers() {
		List<User> users = userService.findAll();
		return userMapper.mapToUserDtoList(users);
	}
	
	@GetMapping("/{userId}")
	public UserDto getUserById(@PathVariable Long userId) {
		User user = userService.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("User id not found: " + userId);
		}
		return userMapper.mapToUserDto(user);
	}
	
	@PutMapping("/{userId}")
	public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
		User user = userMapper.mapToUser(userDto);
		if (user == null) {
			throw new UserNotFoundException("User id not found: " + userId);
		}
		userService.updateUser(userId, user);
		return userMapper.mapToUserDto(user);
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable Long userId) {
		User user = userService.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("User id not found: " + userId);
		}
		userService.deleteUser(userId);
		
		return "Deleted user with id: " + userId;
	}
}
