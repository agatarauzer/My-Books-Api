package com.agatarauzer.myBooks.unit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.security.ERole;
import com.agatarauzer.myBooks.security.Role;
import com.agatarauzer.myBooks.user.User;
import com.agatarauzer.myBooks.user.UserMapper;
import com.agatarauzer.myBooks.user.dto.UserDto;
import com.agatarauzer.myBooks.user.dto.UserFullInfoDto;

@SpringBootTest
public class UserMapperTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void shouldMapUserToUserDto() {
		User user = User.builder()
				.firstName("Tomasz")
				.lastName("Malinowski")
				.email("tomasz.malinowski@gmail.com")
				.password("tom_mal_password")
				.build();
		
		UserDto userDtoMapped = userMapper.mapToUserDto(user);
		
		assertEquals(user.getFirstName(), userDtoMapped.getFirstName());
		assertEquals(user.getLastName(), userDtoMapped.getLastName());
		assertEquals(user.getEmail(), userDtoMapped.getEmail());
		assertEquals(user.getPassword(), userDtoMapped.getPassword());
	}

	@Test
	public void shouldMapUserDtoToUser() {
		UserDto userDto = UserDto.builder()
				.firstName("Tomasz")
				.lastName("Malinowski")
				.email("tomasz.malinowski@gmail.com")
				.password("tom_mal_password")
				.build();
		
		User userMapped = userMapper.mapToUser(userDto);
		
		assertEquals(userDto.getFirstName(), userMapped.getFirstName());
		assertEquals(userDto.getLastName(), userMapped.getLastName());
		assertEquals(userDto.getEmail(), userMapped.getEmail());
		assertEquals(userDto.getPassword(), userMapped.getPassword());
	}
	
	@Test
	public void shouldMapUserListToUserDtoAdminList() {
		List<User> userList = List.of(
				User.builder()
				.id(1L)
				.firstName("Tomasz")
				.lastName("Malinowski")
				.email("tomasz.malinowski@gmail.com")
				.username("tommal")
				.password("tom_mal_password")
				.roles(Set.of(new Role(ERole.ROLE_ADMIN)))
				.build(),
			User.builder()
				.id(2L)
				.firstName("Alicja")
				.lastName("Maj")
				.email("ala.maj@gmail.com")
				.username("agamaj")
				.password("aga_maj_password")
				.roles(Set.of(new Role(ERole.ROLE_USER_PAID)))
				.build());
		
		List<UserFullInfoDto> userDtoListMapped = userMapper.mapToUserFullInfoDtoList(userList);
		
		assertEquals(userList.get(0).getId(), userDtoListMapped.get(0).getId());
		assertEquals(userList.get(0).getFirstName(), userDtoListMapped.get(0).getFirstName());
		assertEquals(userList.get(0).getLastName(), userDtoListMapped.get(0).getLastName());
		assertEquals(userList.get(0).getEmail(), userDtoListMapped.get(0).getEmail());
		assertEquals(userList.get(0).getUsername(), userDtoListMapped.get(0).getUsername());
		assertEquals(userList.get(0).getPassword(), userDtoListMapped.get(0).getPassword());
		assertEquals("ROLE_ADMIN", userDtoListMapped.get(0).getRoles());
		
		assertEquals(userList.get(1).getId(), userDtoListMapped.get(1).getId());
		assertEquals(userList.get(1).getFirstName(), userDtoListMapped.get(1).getFirstName());
		assertEquals(userList.get(1).getLastName(), userDtoListMapped.get(1).getLastName());
		assertEquals(userList.get(1).getEmail(), userDtoListMapped.get(1).getEmail());
		assertEquals(userList.get(1).getUsername(), userDtoListMapped.get(1).getUsername());
		assertEquals(userList.get(1).getPassword(), userDtoListMapped.get(1).getPassword());
		assertEquals("ROLE_USER_PAID", userDtoListMapped.get(1).getRoles());
	}
}
