package com.agatarauzer.myBooks.unit.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.agatarauzer.myBooks.authentication.AuthenticatedUserService;
import com.agatarauzer.myBooks.user.User;
import com.agatarauzer.myBooks.user.UserController;
import com.agatarauzer.myBooks.user.UserMapper;
import com.agatarauzer.myBooks.user.UserService;
import com.agatarauzer.myBooks.user.dto.UserDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserMapper userMapper;

	@MockBean
	private UserService userService;
	
	@MockBean
	AuthenticatedUserService authenticatedUserService;
	
	private Long id;
	private User user;
	private UserDto userDto;

	@BeforeEach
	private void prepareTestData() {
		id = 1L;
		user = User.builder()
			.firstName("Tomasz")
			.lastName("Malinowski")
			.email("tomasz.malinowski@gmail.com")
			.password("tom_mal_password")
			.build();
		userDto = UserDto.builder()
			.firstName("Tomasz")
			.lastName("Malinowski")
			.email("tomasz.malinowski@gmail.com")
			.password("tom_mal_password")
			.build();
	}	
	
	@Test
	public void shouldGetUserById() throws Exception {
		when(userService.findUserById(id)).thenReturn(user);
		when(userMapper.mapToUserDto(user)).thenReturn(userDto);
	
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/users/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.firstName", is("Tomasz")))
			.andExpect(jsonPath("$.email", is("tomasz.malinowski@gmail.com")));
	}
	
	@Test
	public void shouldUpdateUser() throws Exception {
		User userUpdated =  User.builder()
				.firstName("Tomasz")
				.lastName("Malinowski")
				.email("tomasz.malinowski@gmail.com")
				.password("tom_malin_password")
				.build();
		when(userService.updateUser(userUpdated)).thenReturn(userUpdated);
		
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
			.create();
		String jsonUser = gson.toJson(userUpdated);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/v1/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonUser))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.firstName", is("Tomasz")))
			.andExpect(jsonPath("$.password", is("tom_malin_password")));	
	}
	
	@Test
	public void sholudDeleteUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/v1/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200));
		
		verify(userService, times(1)).deleteUser(id);
	}
}
