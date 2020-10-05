package com.solutions.security.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.solutions.security.exception.AlreadyExistsException;
import com.solutions.security.exception.InvalidJWTException;
import com.solutions.security.exception.UserNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(AlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public void handleAlreadyExistsException() {

	}

	@ExceptionHandler(InvalidJWTException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public void handleInvalidOrLoggedOutJWT() {

	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public void handleUserNotFoundException() {

	}
}