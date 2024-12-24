package com.big_lift.palestra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.big_lift.palestra.model.TrainerModel;


@Repository
public interface TrainerRepository extends JpaRepository<TrainerModel, Long>
{
	//Optional<TrainerModel> findByUsername(String username);

}
