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
		
		return UserForAdminDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(roles)
				.build();
	}
	
	public UserDto mapToUserDto(User user) {
		return UserDto.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.build();		
	}
	
	public User mapToUser(UserDto userDto) {
		return User.builder()
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.email(userDto.getEmail())
				.password(userDto.getPassword())
				.build();	
	}
	
	public List<UserForAdminDto> mapToUserForAdminDtoList(List<User> users) {
		return users.stream()
				.map(u -> mapToUserForAdminDto(u))
				.collect(Collectors.toList());
	}
}


