package com.agatarauzer.myBooks.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;
import com.agatarauzer.myBooks.mapper.UserMapper;
import com.agatarauzer.myBooks.service.UserService;
import com.google.gson.Gson;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserMapper userMapper;
	
	@MockBean
	private UserService userService;
	
	
	@Test
	public void shouldGetUsers() throws Exception {
		List<UserDto> userDtoList = List.of(
				new UserDto(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password"),
				new UserDto(2L, "Alicja", "Maj", "ala.maj@gmail.com", "agamaj", "aga_maj_password"));
		List<User> userList = List.of(
				new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password"),
				new User(2L, "Alicja", "Maj", "ala.maj@gmail.com", "agamaj", "aga_maj_password"));
		
		when(userService.findAll()).thenReturn(userList);
		when(userMapper.mapToUserDtoList(userList)).thenReturn(userDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders
						.get("/v1/users")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[1].lastName", is("Maj")))
				.andExpect(jsonPath("$[1].login", is("agamaj")));
	}
	
	@Test
	public void shouldGetUserById() throws Exception {
		Long id = 1L;
		User user = new User(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		UserDto userDto = new UserDto(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		
		when(userService.findUserById(id)).thenReturn(user);
		when(userMapper.mapToUserDto(user)).thenReturn(userDto);
		
		mockMvc.perform(MockMvcRequestBuilders
						.get("/v1/users/{id}", id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.firstName", is("Tomasz")))
				.andExpect(jsonPath("$.email", is("tomasz.malinowski@gmail.com")));
	}
	
	@Test
	public void shouldAddUser() throws Exception {
		Long id = 1L;
		User user = new User(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		UserDto userDto = new UserDto(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		
		when(userService.saveUser(user)).thenReturn(user);
		when(userMapper.mapToUserDto(user)).thenReturn(userDto);
		
		Gson gson = new Gson();
		String jsonUserDto = gson.toJson(userDto);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(jsonUserDto))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.lastName", is("Malinowski")))
				.andExpect(jsonPath("$.login", is("tommal")));	
	}
	
	@Test
	public void shouldUpdateUser() throws Exception {
		Long id = 1L;
		User user = new User(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		User userUpdated = new User(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommalin", "tomasz_malin_password");
		UserDto userUpdatedDto = new UserDto(id, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommalin", "tomasz_malin_password");
		
		when(userService.updateUser(id, user)).thenReturn(userUpdated);
		
		Gson gson = new Gson();
		String jsonUserDto = gson.toJson(userUpdatedDto);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/v1/users/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(jsonUserDto))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.login", is("tommalin")))
				.andExpect(jsonPath("$.password", is("tomasz_malin_password")));	
	}
	
	@Test
	public void sholudDeleteUser() throws Exception {
		Long id = 1L;
	
		doNothing().when(userService).deleteUser(id);
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/v1/users/{id}", id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
	}
}
