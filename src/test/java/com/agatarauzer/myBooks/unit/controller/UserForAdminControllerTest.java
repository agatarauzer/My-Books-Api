package com.agatarauzer.myBooks.unit.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

import com.agatarauzer.myBooks.authentication.role.ERole;
import com.agatarauzer.myBooks.authentication.role.Role;
import com.agatarauzer.myBooks.user.User;
import com.agatarauzer.myBooks.user.UserForAdminController;
import com.agatarauzer.myBooks.user.UserMapper;
import com.agatarauzer.myBooks.user.UserService;
import com.agatarauzer.myBooks.user.dto.UserFullInfoDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringJUnitWebConfig
@WebMvcTest(UserForAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserForAdminControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserMapper userMapper;

	@MockBean
	private UserService userService;
	
	private Long id;
	private User user;
	private UserFullInfoDto userDto;
	private List<User> userList;
	private List<UserFullInfoDto> userDtoList;
	
	
	@BeforeEach
	private void prepareTestData() {
		id = 1L;
		user = User.builder()
			.id(1L)
			.firstName("Tomasz")
			.lastName("Malinowski")
			.email("tomasz.malinowski@gmail.com")
			.username("tommal")
			.password("tom_mal_password")
			.roles(Set.of(new Role(ERole.ROLE_ADMIN)))
			.build();
		userDto = UserFullInfoDto.builder()
			.id(1L)
			.firstName("Tomasz")
			.lastName("Malinowski")
			.email("tomasz.malinowski@gmail.com")
			.username("tommal")
			.password("tom_mal_password")
			.roles("ROLE_ADMIN")
			.build();
		userList = List.of(
			user,
			User.builder()
				.id(2L)
				.firstName("Alicja")
				.lastName("Maj")
				.email("ala.maj@gmail.com")
				.username("agamaj")
				.password("aga_maj_password")
				.roles(Set.of(new Role(ERole.ROLE_USER_PAID)))
				.build());
		userDtoList = List.of(
			userDto,
			UserFullInfoDto.builder()
				.id(2L)
				.firstName("Alicja")
				.lastName("Maj")
				.email("ala.maj@gmail.com")
				.username("agamaj")
				.password("aga_maj_password")
				.roles("ROLE_USER_PAID")
				.build());
	}
	
	@Test
	public void shouldGetUsers() throws Exception {
		when(userService.findAll(0, 5, "id", "asc")).thenReturn(userList);
		when(userMapper.mapToUserFullInfoDtoList(userList)).thenReturn(userDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/admin/users")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[1].lastName", is("Maj")));	
	}
	
	@Test
	public void shouldGetUserById() throws Exception {
		when(userService.findUserById(id)).thenReturn(user);
		when(userMapper.mapToUserFullInfoDto(user)).thenReturn(userDto);
	
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/admin/users/1")
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
				.roles(Set.of(new Role(ERole.ROLE_USER_LIMITED)))
				.build();
		when(userService.updateUser(userUpdated)).thenReturn(userUpdated);
			
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
			.create();
		String jsonUser = gson.toJson(userUpdated);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/v1/admin/users/{id}", id)
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
				.delete("/v1/admin/users/{id}", id)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200));
		
		verify(userService, times(1)).deleteUser(id);
	}
}
