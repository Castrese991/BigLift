package com.big_lift.palestra.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.big_lift.palestra.client.CustomerClient;
import com.big_lift.palestra.client.CustomerResponse;
import com.big_lift.palestra.dto.TrainerDTO;

import com.big_lift.palestra.exception.UserAlreadyExistsException;
import com.big_lift.palestra.model.TrainerModel;
import com.big_lift.palestra.model.UserModel;
import com.big_lift.palestra.repository.TrainerRepository;
import com.big_lift.palestra.repository.UserRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TrainerService
{
	private final TrainerRepository trainerRepository;
	private final CustomerClient customerClient;
	private final UserRepository userRepository;


	public TrainerDTO assignTrainer(Long idTrainer, Long idCustomer)
	{
		try {
			Optional<TrainerModel> trainerModelOptional = trainerRepository.findById(idTrainer);

			if (trainerModelOptional.isPresent()) {
				Optional<CustomerResponse> customerOptional = customerClient.getCustomerById(idCustomer);

				if (customerOptional.isPresent()) {
					TrainerModel trainer = trainerModelOptional.get();
					trainer.getCustomerIds().add(idCustomer);

					trainerRepository.save(trainer);

					// Utilizza il CustomerResponse restituito dalla chiamata Feign
					CustomerResponse updatedCustomer = customerClient.assignTrainerToCustomer(idCustomer, trainer.getIdTrainer());
					System.out.println("Cliente aggiornato: " + updatedCustomer);

					return TrainerDTO.builder()
							.id(trainer.getIdTrainer())
							.trainerName(trainer.getTrainerName())
							.trainerLastName(trainer.getTrainerLastName())
							.yearsOfExperience(trainer.getYearsOfExperience())
							.customerIds(trainer.getCustomerIds())
							.locationTrainer(trainer.getLocationTrainer())
							.build();
				} else {
					throw new IllegalArgumentException("Cliente con ID " + idCustomer + " non trovato.");
				}
			} else {
				throw new IllegalArgumentException("Trainer con ID " + idTrainer + " non trovato.");
			}
		} catch (FeignException.NotFound e) {
			throw new IllegalArgumentException("Cliente o Trainer non trovato nel microservizio customer-service", e);
		} catch (FeignException e) {
			throw new RuntimeException("Errore durante la comunicazione con il customer-service", e);
		}
	}

	public TrainerDTO createTrainer(final TrainerDTO createTrainer)
	{
		//verifyTrainerToCreate(createTrainer);
		Optional<UserModel> user = userRepository.findById(createTrainer.getIdUser());
		TrainerModel t = TrainerModel.builder()
				.trainerName(createTrainer.getTrainerName())
				.trainerLastName(createTrainer.getTrainerLastName())
				.dateOfBirthTrainer(createTrainer.getDateOfBirth())
				.yearsOfExperience(createTrainer.getYearsOfExperience())
				.locationTrainer(createTrainer.getLocationTrainer())
				.certifications(createTrainer.getCertifications())
				.customerIds(createTrainer.getCustomerIds())
				.user(user.get())
				.build();


		trainerRepository.save(t);
		System.out.println("Trainer created with name: " + t.getTrainerName() + " " + t.getTrainerLastName());
		return new TrainerDTO().builder()
				.id(t.getIdTrainer())
				.trainerName(t.getTrainerName())
				.trainerLastName(t.getTrainerLastName())
				.dateOfBirth(t.getDateOfBirthTrainer())
				.yearsOfExperience(t.getYearsOfExperience())
				.certifications(t.getCertifications())
				.locationTrainer(t.getLocationTrainer())
				.idUser(t.getUser().getId())
				.build();
	}

	private void verifyTrainerToCreate(TrainerDTO trainerDTO)
	{
		if (trainerRepository.findById(trainerDTO.getId()).isPresent())
		{
			throw new UserAlreadyExistsException("Nome utente già presente. Scegli un altro username.");
		}
	}
}
