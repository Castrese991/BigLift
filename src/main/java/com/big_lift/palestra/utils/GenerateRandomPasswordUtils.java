package com.big_lift.palestra.utils;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class GenerateRandomPasswordUtils
{
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
	private static final int PASSWORD_LENGTH = 12;

	private GenerateRandomPasswordUtils() {
		throw new UnsupportedOperationException("Utility class should not be instantiated");
	}

	public static String generatePwd(){
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = random.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(index));
		}

		return password.toString();
	}

	public static String generateHashedPassword(String rawPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(rawPassword);
	}
}
