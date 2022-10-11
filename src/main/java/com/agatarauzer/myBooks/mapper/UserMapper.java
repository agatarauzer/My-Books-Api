package com.agatarauzer.myBooks.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;
import com.agatarauzer.myBooks.dto.UserDtoAdmin;

@Service
public class UserMapper {
	
	public UserDtoAdmin mapToUserDtoAdmin(User user) {
		String roles = user.getRoles().stream()
				.map(role -> role.toString())
				.collect(Collectors.joining(", "));
		
		return new UserDtoAdmin(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getUsername(),
				user.getPassword(),
				roles);
	}
	
	public UserDto mapToUserDto(User user) {
		return new UserDto(
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPassword());		
	}
	
	public User mapToUser(UserDto userDto) {
		return new User(
				userDto.getFirstName(),
				userDto.getLastName(),
				userDto.getEmail(),
				userDto.getPassword());
	}
	
	public List<UserDtoAdmin> mapToUserDtoAdminList(List<User> users) {
		return users.stream()
				.map(u -> mapToUserDtoAdmin(u))
				.collect(Collectors.toList());
	}
}


