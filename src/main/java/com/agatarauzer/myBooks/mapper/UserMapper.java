package com.agatarauzer.myBooks.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;
import com.agatarauzer.myBooks.dto.UserForAdminDto;

@Service
public class UserMapper {
	
	public UserForAdminDto mapToUserForAdminDto(User user) {
		String roles = user.getRoles().stream()
				.map(role -> role.toString())
				.collect(Collectors.joining(", "));
		
		return new UserForAdminDto(
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
	
	public List<UserForAdminDto> mapToUserForAdminDtoList(List<User> users) {
		return users.stream()
				.map(u -> mapToUserForAdminDto(u))
				.collect(Collectors.toList());
	}
}


