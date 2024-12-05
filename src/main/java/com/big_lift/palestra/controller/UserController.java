package com.big_lift.palestra.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.big_lift.palestra.dto.UserDTO;
import com.big_lift.palestra.exception.UserAlreadyExistsException;
import com.big_lift.palestra.model.UserModel;
import com.big_lift.palestra.repository.UserRepository;
import com.big_lift.palestra.service.UserService;
import com.big_lift.palestra.view.Views;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api")
public class UserController {

	private final UserRepository userRepository;

	private final UserService userService;

	public UserController(final UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@GetMapping("/users")
	@Operation(summary = "Ottieni tutti gli utenti", description = "Restituisce una lista di tutti gli utenti presenti nel database")
	public ResponseEntity<Page<UserDTO>> getAllUsers(@PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				JpaSort.unsafe("createdAt"));
		Page<UserDTO> users = userService.getAllUsers(sortedPageable);
		return ResponseEntity.ok(users);
	}

	@PostMapping("/create")
	@Operation(summary = "Crea un nuovo utente", description = "Salva un nuovo utente nel database e restituisce l'utente creato")
	public ResponseEntity<UserDTO> createUser(@JsonView(Views.CreateView.class) @RequestBody UserDTO userDTO)
	{
		try
		{
			UserDTO createdUser = userService.createUser(userDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		}
		catch (UserAlreadyExistsException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Elimina un utente", description = "Elimina un utente definitivamente dal sistema")
	public void deleteUser(@PathVariable Long id){userService.deleteUser(id);}

	@PostMapping("/update")
	@Operation(summary = "Modifica un utente", description = "Aggiorna solamente il ruolo di un utente esistente")
	public UserDTO updateUser(@JsonView(Views.UpdateView.class) @RequestBody UserDTO userDTO){
		return userService.updateUserRole(userDTO);
	}

	@GetMapping("/findUser/{id}")
	@Operation(summary = "Modifica un utente", description = "Aggiorna un utente esistente")
	public Optional<UserModel> findUser(@PathVariable Long id){return userRepository.findById(id);}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
