package com.big_lift.palestra.client;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse
{
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Long trainerId;
	private Instant updatedAt;
	private String subscriptionType;
	private String fiscalCode;
	private Instant createdAt;
}
