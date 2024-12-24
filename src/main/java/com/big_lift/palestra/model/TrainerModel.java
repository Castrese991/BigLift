package com.big_lift.palestra.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "TrainerModel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TrainerModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTrainer;
	@Column(name = "trainer_name")
	private String trainerName;
	@Column(name = "trainer_lastname")
	private String trainerLastName;
	@Column(name = "date_of_birth")
	private Date dateOfBirthTrainer;
	@Column(name = "location")
	private String locationTrainer;
	@Column(name = "fiscal_code")
	private String trainerFiscalCode;
	@ElementCollection
	@Column(name = "customer_id")
	private List<Long> customerIds = new ArrayList<>();
	@ElementCollection
	@Column(name = "specialization")
	private List<String> specializations = new ArrayList<>();
	@ElementCollection
	@Column(name = "certification")
	private List<String> certifications = new ArrayList<>();
	@Column(name = "years_of_experience", nullable = false)
	private int yearsOfExperience;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserModel user;

}
