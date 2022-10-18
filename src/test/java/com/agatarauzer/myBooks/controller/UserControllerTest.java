package com.agatarauzer.myBooks.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.agatarauzer.myBooks.domain.ERole;
import com.agatarauzer.myBooks.domain.Role;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.UserDto;
import com.agatarauzer.myBooks.dto.UserDtoAdmin;
import com.agatarauzer.myBooks.mapper.UserMapper;
import com.agatarauzer.myBooks.service.UserService;
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

	@Test
	public void shouldGetUsers() throws Exception {
		List<UserDtoAdmin> userDtoList = List.of(
				new UserDtoAdmin(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password", "ROLE_ADMIN"),
				new UserDtoAdmin(2L, "Alicja", "Maj", "ala.maj@gmail.com", "agamaj", "aga_maj_password", "ROLE_USER_PAID"));
		List<User> userList = List.of(
				new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password", Set.of(new Role(ERole.ROLE_ADMIN))),
				new User(2L, "Alicja", "Maj", "ala.maj@gmail.com", "agamaj", "aga_maj_password", Set.of(new Role(ERole.ROLE_USER_PAID))));
		
		when(userService.findAll()).thenReturn(userList);
		when(userMapper.mapToUserDtoAdminList(userList)).thenReturn(userDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders
						.get("/v1/users")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[1].lastName", is("Maj")));	
	}
	
	@Test
	public void shouldGetUserById() throws Exception {
		Long id = 1L;
		User user = new User("Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tom_mal_password");
		UserDto userDto = new UserDto("Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tom_mal_password");
		
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
		Long id = 1L;
		User userUpdated = new User("Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tomasz_malin_password");
		when(userService.updateUser(id, userUpdated)).thenReturn(userUpdated);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
				.create();
		String jsonUser = gson.toJson(userUpdated);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/v1/users/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(jsonUser))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.firstName", is("Tomasz")))
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
