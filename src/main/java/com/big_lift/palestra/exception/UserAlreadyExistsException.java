package com.big_lift.palestra.exception;

public class UserAlreadyExistsException extends RuntimeException
{
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
