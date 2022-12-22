package com.agatarauzer.myBooks.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final UserMapper userMapper;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserFullInfoDto>> getUsers(@RequestParam(defaultValue = "0", required = false) int page,
					@RequestParam(defaultValue = "5", required = false) int size,
					@RequestParam(defaultValue = "id", required = false) String sortBy,
					@RequestParam(defaultValue = "asc", required = false) String sortDir) {
		List<User> users = userService.findAll(page, size, sortBy, sortDir);
		return ResponseEntity.ok(userMapper.mapToUserFullInfoDtoList(users));
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable final Long userId) {
		User user = userService.findUserById(userId);
		return  ResponseEntity.ok(userMapper.mapToUserDto(user));
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable final Long userId, @RequestBody final UserDto userDto) {
		User user = userMapper.mapToUser(userDto);
		userService.updateUser(userId, user);
		return ResponseEntity.ok(userDto);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable final Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok().body("Deleted user with id: " + userId);
	}
}
