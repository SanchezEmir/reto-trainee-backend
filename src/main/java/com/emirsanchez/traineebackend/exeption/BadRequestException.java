package com.emirsanchez.traineebackend.exeption;

public class BadRequestException extends RuntimeException {
	
	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable exception) {
		super(message, exception);
	}

}
