package com.agatarauzer.myBooks.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.authentication.AuthenticatedUserService;
import com.agatarauzer.myBooks.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@PreAuthorize("@authenticatedUserService.hasId(#userId)")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final UserMapper userMapper;
	private final AuthenticatedUserService authenticatedUserService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable final Long userId) {
		User user = userService.findUserById(userId);
		return ResponseEntity.ok(userMapper.mapToUserDto(user));
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
