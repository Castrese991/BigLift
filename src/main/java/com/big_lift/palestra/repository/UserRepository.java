package com.big_lift.palestra.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.big_lift.palestra.dto.UserDTO;
import com.big_lift.palestra.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findByUsername(String username);

	@Query("SELECT new com.big_lift.palestra.dto.UserDTO(u.id, u.username, u.role, u.email, u.createdAt) FROM UserModel u")
	Page<UserDTO> getAllUser(Pageable pageable);

	Optional<UserModel> findByEmail(String email);
}
