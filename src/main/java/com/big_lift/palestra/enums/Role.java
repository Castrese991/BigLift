package com.big_lift.palestra.enums;

public enum Role {
	ADMIN("Administrator"),
	TRAINER("Trainer"),
	RECEPTIONIST("Receptionist");

	private final String description;

	Role(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
