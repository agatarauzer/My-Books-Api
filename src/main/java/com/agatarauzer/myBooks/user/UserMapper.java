package com.agatarauzer.myBooks.user;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agatarauzer.myBooks.user.dto.UserDto;
import com.agatarauzer.myBooks.user.dto.UserFullInfoDto;

@Component
public class UserMapper {
	
	public UserFullInfoDto mapToUserFullInfoDto(User user) {
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
				.registrationDate(user.getRegistrationDate())
				.enabled(user.getEnabled())
				.roles(roles)
				.build();
	}
	
	public UserDto mapToUserDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.password(user.getPassword())
				.build();		
	}
	
	public User mapToUser(UserDto userDto) {
		return User.builder()
				.id(userDto.getId())
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.email(userDto.getEmail())
				.password(userDto.getPassword())
				.build();	
	}
	
	public List<UserFullInfoDto> mapToUserFullInfoDtoList(List<User> users) {
		return users.stream()
				.map(u -> mapToUserFullInfoDto(u))
				.collect(Collectors.toList());
	}
}


