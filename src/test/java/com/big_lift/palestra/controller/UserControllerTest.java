package com.big_lift.palestra.controller;

import com.big_lift.palestra.model.UserModel;
import com.big_lift.palestra.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@Test
	@WithMockUser(username = "user", roles = "USER")
	void testGetAllUsers() throws Exception {
		when(userRepository.findAll()).thenReturn(Arrays.asList(
				new UserModel(1L, "user1", "password", "ADMIN"),
				new UserModel(2L, "user2", "password", "USER")
		));

		mockMvc.perform(get("/api/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].username").value("user1"))
				.andExpect(jsonPath("$[1].username").value("user2"));
	}

//	@Test
//	@WithMockUser(username = "user", roles = "USER")
//	void testCreateUser() throws Exception
//	{
//		UserModel newUser = new UserModel(1L, "newUser", "password", "ADMIN");
//
//		when(userRepository.save((newUser))).thenReturn(newUser);
//
//		mockMvc.perform(post("/api/create")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content("""
//								    {
//								        "username": "newUser",
//								        "password": "password",
//								        "role": "ADMIN"
//								    }
//								""")
//						.with(csrf()))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.username").value("newUser"));
//	}
//
//	@Test
//	@WithMockUser(username = "user", roles = "USER")
//	public void testDeleteUser() throws Exception {
//		// Mock del repository
//		Mockito.doNothing().when(userRepository).deleteById(1L);
//
//		// Test della richiesta DELETE
//		mockMvc.perform(delete("/api/1")
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//
//		// Verifica che il metodo deleteById sia stato chiamato
//		verify(userRepository, times(1)).deleteById(1L);
//	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	void testFindUser() throws Exception {
		UserModel user = new UserModel(1L, "foundUser", "foundPass", "founduser@example.com");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		mockMvc.perform(get("/api/findUser/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("foundUser"));

		verify(userRepository, times(1)).findById(1L);
	}
}