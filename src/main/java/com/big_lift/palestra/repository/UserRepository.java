package com.big_lift.palestra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.big_lift.palestra.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findByUsername(String username);
}
