package com.big_lift.palestra.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.big_lift.palestra.dto.UserDTO;
import com.big_lift.palestra.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findByUsername(String username);

	@Query("SELECT new com.big_lift.palestra.dto.UserDTO(u.id, u.username, u.role, u.email, u.createdAt, u.password) FROM UserModel u")
	Page<UserDTO> getAllUser(Pageable pageable);

	Optional<UserModel> findByEmail(String email);

	@Query("SELECT new com.big_lift.palestra.dto.UserDTO(u.id, u.username, u.role, u.email, u.createdAt, u.password) " +
			"FROM UserModel u WHERE u.username = :username AND u.email = :email")
	UserDTO getUserByUsernameEmail(@Param("username") String username, @Param("email") String email);

	//UserDTO assignTrainerToCustomer(@Param())
}
