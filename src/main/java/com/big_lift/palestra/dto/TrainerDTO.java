package com.big_lift.palestra.dto;

import java.util.Date;
import java.util.List;

import com.big_lift.palestra.view.Views;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDTO
{
	@JsonView(Views.Public.class)
	private Long id;
	@JsonView(Views.CreateView.class)
	private Date dateOfBirth;
	@JsonView({ Views.CreateView.class, Views.UpdateView.class })
	private int yearsOfExperience;
	@JsonView(Views.CreateView.class)
	private String trainerName;
	@JsonView(Views.CreateView.class)
	private String trainerLastName;
	@JsonView({ Views.CreateView.class, Views.UpdateView.class })
	private String locationTrainer;
	@JsonView(Views.Public.class)
	private List<Long> customerIds;
	@JsonView({ Views.CreateView.class, Views.UpdateView.class })
	private List<String>certifications;
	@JsonView({ Views.CreateView.class, Views.UpdateView.class })
	private Long idUser;

}
