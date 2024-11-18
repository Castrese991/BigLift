package com.big_lift.palestra.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.big_lift.palestra.model.UserModel;
import com.big_lift.palestra.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api")
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	@Operation(summary = "Ottieni tutti gli utenti", description = "Restituisce una lista di tutti gli utenti presenti nel database")
	public List<UserModel> getAllUsers(){
		return userRepository.findAll();
	}

	@PostMapping("/create")
	@Operation(summary = "Crea un nuovo utente", description = "Salva un nuovo utente nel database e restituisce l'utente creato")
	public UserModel createUser(@RequestBody UserModel userModel){
		return userRepository.save(userModel);
	}

}
