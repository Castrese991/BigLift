package com.big_lift.palestra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.big_lift.palestra.dto.TrainerDTO;
import com.big_lift.palestra.dto.UserDTO;
import com.big_lift.palestra.exception.UserAlreadyExistsException;
import com.big_lift.palestra.service.JwtUtils;
import com.big_lift.palestra.service.TrainerService;
import com.big_lift.palestra.service.UserService;
import com.big_lift.palestra.view.Views;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

	private final UserService userService;

	private final TrainerService trainerService;

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtil;


	public UserController(final UserService userService, final TrainerService trainerService,
			final AuthenticationManager authenticationManager, final JwtUtils jwtUtil) {
		this.userService = userService;
		this.trainerService = trainerService;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		log.info(" sono nel UserController login method");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return jwtUtil.generateToken(userDetails.getUsername());
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

	@GetMapping("/findUser/{username}")
	@Operation(summary = "Trova un utente", description = "Trova un utente esistente in base a username ed email")
	public ResponseEntity<UserDTO> findUser(@PathVariable String username, @RequestParam String email) {
		return userService.getUser(username, email);
	}

	@PatchMapping("/assignTrainer/{idTrainer}/{idCustomer}")
	@Operation(summary = "Assegna un trainer ad un cliente", description = "Assegna il trainer a un cliente")
	public ResponseEntity<TrainerDTO> assignTrainerToCustomer(@PathVariable Long idTrainer, @PathVariable Long idCustomer) {
			try
			{
				TrainerDTO trainer = trainerService.assignTrainer(idTrainer, idCustomer);
				return ResponseEntity.status(HttpStatus.CREATED).body(trainer);
			}
			catch (UserAlreadyExistsException e)
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
	}

	@PostMapping("/createTrainer")
	@Operation(summary = "Crea un nuovo utente", description = "Salva un nuovo utente nel database e restituisce l'utente creato")
	public ResponseEntity<TrainerDTO> createTrainer(@JsonView(Views.CreateView.class) @RequestBody TrainerDTO trainerDTO)
	{
		try
		{
			TrainerDTO createTrainer = trainerService.createTrainer(trainerDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(createTrainer);
		}
		catch (UserAlreadyExistsException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}


	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
