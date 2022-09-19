package com.agatarauzer.myBooks.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;

@Service
public class UserMapper {
	
	public UserDto mapToUserDto(User user) {
		return new UserDto(user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getLogin(),
				user.getPassword());		
	}
	
	public User mapToUser(UserDto userDto) {
		return new User(userDto.getId(),
				userDto.getFirstName(),
				userDto.getLastName(),
				userDto.getEmail(),
				userDto.getLogin(),
				userDto.getPassword());
	}
	
	public List<UserDto> mapToUserDtoList(List<User> users) {
		return users.stream()
				.map(u -> mapToUserDto(u))
				.collect(Collectors.toList());
	}
}


