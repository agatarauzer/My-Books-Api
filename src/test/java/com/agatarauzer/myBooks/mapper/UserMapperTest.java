package com.agatarauzer.myBooks.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;

@SpringBootTest
public class UserMapperTest {
	
	@Autowired
	UserMapper userMapper;
	
	@Test
	public void shouldMapUserToUserDto() {
		User user = new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		
		UserDto userDto = userMapper.mapToUserDto(user);
		
		assertEquals(user.getId(), userDto.getId());
		assertEquals(user.getFirstName(), userDto.getFirstName());
		assertEquals(user.getLastName(), userDto.getLastName());
		assertEquals(user.getEmail(), userDto.getEmail());
		assertEquals(user.getLogin(), userDto.getLogin());
		assertEquals(user.getPassword(), userDto.getPassword());
	}

	@Test
	public void shouldMapUserDtoToUser() {
		UserDto userDto = new UserDto(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		
		User user = userMapper.mapToUser(userDto);
		
		assertEquals(userDto.getId(), user.getId());
		assertEquals(userDto.getFirstName(), user.getFirstName());
		assertEquals(userDto.getLastName(), user.getLastName());
		assertEquals(userDto.getEmail(), user.getEmail());
		assertEquals(userDto.getLogin(), user.getLogin());
		assertEquals(userDto.getPassword(), user.getPassword());
	}
	
	@Test
	public void shouldMapUserListToUserDtoList() {
		List<User> userList = List.of(
				new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password"),
				new User(2L, "Alicja", "Maj", "ala.maj@gmail.com", "agamaj", "aga_maj_password"));
		
		List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);
		
		assertEquals(userList.get(0).getId(), userDtoList.get(0).getId());
		assertEquals(userList.get(0).getFirstName(), userDtoList.get(0).getFirstName());
		assertEquals(userList.get(0).getLastName(), userDtoList.get(0).getLastName());
		assertEquals(userList.get(0).getEmail(), userDtoList.get(0).getEmail());
		assertEquals(userList.get(0).getLogin(), userDtoList.get(0).getLogin());
		assertEquals(userList.get(0).getPassword(), userDtoList.get(0).getPassword());
		assertEquals(userList.get(1).getId(), userDtoList.get(1).getId());
		assertEquals(userList.get(1).getFirstName(), userDtoList.get(1).getFirstName());
		assertEquals(userList.get(1).getLastName(), userDtoList.get(1).getLastName());
		assertEquals(userList.get(1).getEmail(), userDtoList.get(1).getEmail());
		assertEquals(userList.get(1).getLogin(), userDtoList.get(1).getLogin());
		assertEquals(userList.get(1).getPassword(), userDtoList.get(1).getPassword());
	}
}
