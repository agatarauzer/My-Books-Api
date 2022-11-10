package com.agatarauzer.myBooks.mapper;


import java.util.List;
import java.util.stream.Collectors;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;
import com.agatarauzer.myBooks.dto.UserFullInfoDto;


public class UserMapper {
	
	private UserMapper() {
	}
	
	public static UserFullInfoDto mapToUserFullInfoDto(User user) {
		String roles = user.getRoles().stream()
				.map(role -> role.toString())
				.collect(Collectors.joining(", "));
		
		return UserFullInfoDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(roles)
				.build();
	}
	
	public static UserDto mapToUserDto(User user) {
		return UserDto.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.build();		
	}
	
	public static User mapToUser(UserDto userDto) {
		return User.builder()
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.email(userDto.getEmail())
				.password(userDto.getPassword())
				.build();	
	}
	
	public static List<UserFullInfoDto> mapToUserFullInfoDtoList(List<User> users) {
		return users.stream()
				.map(u -> mapToUserFullInfoDto(u))
				.collect(Collectors.toList());
	}
}


