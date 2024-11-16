package com.big_lift.palestra.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.big_lift.palestra.model.UserModel;
import com.big_lift.palestra.repository.UserRepository;


@RestController
@RequestMapping("/api")
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public List<UserModel> getAllUsers(){
		return userRepository.findAll();
	}

	@PostMapping("/create")
	public UserModel createUser(@RequestBody UserModel userModel){
		return userRepository.save(userModel);
	}

}
