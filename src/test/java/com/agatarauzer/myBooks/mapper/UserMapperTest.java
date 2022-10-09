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
		
		UserDto userDtoMapped = userMapper.mapToUserDto(user);
		
		assertEquals(user.getId(), userDtoMapped.getId());
		assertEquals(user.getFirstName(), userDtoMapped.getFirstName());
		assertEquals(user.getLastName(), userDtoMapped.getLastName());
		assertEquals(user.getEmail(), userDtoMapped.getEmail());
		assertEquals(user.getUsername(), userDtoMapped.getUsername());
		assertEquals(user.getPassword(), userDtoMapped.getPassword());
	}

	@Test
	public void shouldMapUserDtoToUser() {
		UserDto userDto = new UserDto(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		
		User userMapped = userMapper.mapToUser(userDto);
		
		assertEquals(userDto.getId(), userMapped.getId());
		assertEquals(userDto.getFirstName(), userMapped.getFirstName());
		assertEquals(userDto.getLastName(), userMapped.getLastName());
		assertEquals(userDto.getEmail(), userMapped.getEmail());
		assertEquals(userDto.getUsername(), userMapped.getUsername());
		assertEquals(userDto.getPassword(), userMapped.getPassword());
	}
	
	@Test
	public void shouldMapUserListToUserDtoList() {
		List<User> userList = List.of(
				new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password"),
				new User(2L, "Alicja", "Maj", "ala.maj@gmail.com", "agamaj", "aga_maj_password"));
		
		List<UserDto> userDtoListMapped = userMapper.mapToUserDtoList(userList);
		
		assertEquals(userList.get(0).getId(), userDtoListMapped.get(0).getId());
		assertEquals(userList.get(0).getFirstName(), userDtoListMapped.get(0).getFirstName());
		assertEquals(userList.get(0).getLastName(), userDtoListMapped.get(0).getLastName());
		assertEquals(userList.get(0).getEmail(), userDtoListMapped.get(0).getEmail());
		assertEquals(userList.get(0).getUsername(), userDtoListMapped.get(0).getUsername());
		assertEquals(userList.get(0).getPassword(), userDtoListMapped.get(0).getPassword());
		assertEquals(userList.get(1).getId(), userDtoListMapped.get(1).getId());
		assertEquals(userList.get(1).getFirstName(), userDtoListMapped.get(1).getFirstName());
		assertEquals(userList.get(1).getLastName(), userDtoListMapped.get(1).getLastName());
		assertEquals(userList.get(1).getEmail(), userDtoListMapped.get(1).getEmail());
		assertEquals(userList.get(1).getUsername(), userDtoListMapped.get(1).getUsername());
		assertEquals(userList.get(1).getPassword(), userDtoListMapped.get(1).getPassword());
	}
}
