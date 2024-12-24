package com.big_lift.palestra.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big_lift.palestra.dto.UserDTO;
import com.big_lift.palestra.exception.UserAlreadyExistsException;
import com.big_lift.palestra.model.UserModel;
import com.big_lift.palestra.repository.UserRepository;


@Service
public class UserService
{
	private final UserRepository userRepository;

	private final PasswordEncoder encoder;

	@Autowired
	public UserService(UserRepository userRepository, final PasswordEncoder encoder)
	{
		this.userRepository = userRepository;
		this.encoder = encoder;
	}


	@Transactional(readOnly = true)
	public Page<UserDTO> getAllUsers(Pageable pageable)
	{
		return userRepository.getAllUser(pageable);
	}

	public UserDTO createUser(UserDTO userDTO)
	{
		verifyUserToCreate(userDTO);

		UserModel u = UserModel.builder()
				.username(userDTO.getUsername())
				.password(encoder.encode(userDTO.getPassword()))
				.email(userDTO.getEmail())
				.role(userDTO.getRole())
				.build();


		userRepository.save(u);
		System.out.println("User created with ID: " + u.getId() + " and CreatedAt: " + u.getCreatedAt());
		return new UserDTO().builder()
				.id(u.getId())
				.email(u.getEmail())
				.username(u.getUsername())
				.password(u.getPassword())
				.createdAt(u.getCreatedAt())
				.role(u.getRole()).build();
	}

	public void deleteUser(Long id)
	{
		if (userRepository.existsById(id))
		{
			userRepository.deleteById(id);
		}
	}

	public UserDTO updateUserRole(UserDTO userDTO)
	{
		try
		{
			Optional<UserModel> optionalUser = userRepository.findByUsername(userDTO.getUsername());
			if (optionalUser.isPresent())
			{
				UserModel userToUpdate = optionalUser.get();
				userToUpdate.setRole(userDTO.getRole());
				userRepository.save(userToUpdate);

				return UserDTO.builder()
						.id(userToUpdate.getId())
						.email(userToUpdate.getEmail())
						.username(userToUpdate.getUsername())
						.createdAt(userToUpdate.getCreatedAt())
						.role(userToUpdate.getRole())
						.build();
			}
			else
			{
				throw new RuntimeException("User not found");
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error updating user role", e);
		}
	}

	@Transactional(readOnly = true)
	public ResponseEntity<UserDTO> getUser(String username, String email) {
		if (username == null || username.isEmpty() || email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Username ed email sono obbligatori");
		}

		try {
			UserDTO dto = userRepository.getUserByUsernameEmail(username, email);
			if (dto == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			throw new RuntimeException("Errore durante la ricerca dell'utente", e);
		}
	}

	private void verifyUserToCreate(UserDTO userDTO)
	{
		if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
		{
			throw new UserAlreadyExistsException("Nome utente già presente. Scegli un altro username.");
		}

		if (userRepository.findByEmail(userDTO.getEmail()).isPresent())
		{
			throw new UserAlreadyExistsException("Email già in uso. Scegli un'altra email.");
		}
	}

	public UserModel findUserByEmail(final String email)
	{
		return userRepository.findByEmail(email).get();
	}

}
