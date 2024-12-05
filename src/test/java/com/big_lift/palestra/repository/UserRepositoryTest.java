package com.big_lift.palestra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.big_lift.palestra.model.UserModel;


@DataJpaTest
public class UserRepositoryTest {

//	@Autowired
//	private UserRepository userRepository;
//
//	@BeforeEach
//	public void setUp() {
//		UserModel user = new UserModel();
//		user.setUsername("testuser");
//		user.setPassword("password");
//		user.setRole("USER");
//		userRepository.save(user);
//	}
//
//	@Test
//	public void testGetAllUsers(){
//		Iterable<UserModel> users = userRepository.findAll();
//		assertNotNull(users);
//	}
//
//	@Test
//	public void testGetUser(){
//		Long id = 123L;
//		Optional<UserModel> user = userRepository.findById(id);
//		assertNotNull(user);
//	}
//
//	@Test
//	public void testDeleteUserById(){
//		UserModel user = userRepository.findAll().get(0);
//		assertNotNull(user);
//		Long userId = user.getId();
//		Optional<UserModel> fetchedUser = userRepository.findById(userId);
//		assertTrue(fetchedUser.isPresent());
//
//		userRepository.deleteById(userId);
//
//		Optional<UserModel> deletedUser = userRepository.findById(userId);
//		assertFalse(deletedUser.isPresent());
//	}
//
//	@Test
//	public void testCreateUser(){
//		UserModel user = new UserModel();
//		user.setUsername("newuser");
//		user.setPassword("newpass");
//		user.setRole("USER");
//
//		UserModel saveUser = userRepository.save(user);
//		assertNotNull(saveUser.getId());
//		assertEquals("newuser", saveUser.getUsername());
//		assertEquals("newpass", saveUser.getPassword());
//		assertEquals("USER", saveUser.getRole());
//	}
}
