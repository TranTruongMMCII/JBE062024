package com.r2s.java_backend_06.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.r2s.java_backend_06.exception.UserNotFoundException;
import com.r2s.java_backend_06.response.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ErrorResponse handle(final Exception exception) {
		exception.printStackTrace();
		return ErrorResponse.of("internal.server", exception.getMessage());
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = UserNotFoundException.class)
	public ErrorResponse handle(final UserNotFoundException exception) {
		return ErrorResponse.of(exception.getCode(), exception.getMessage());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ErrorResponse handle(final ConstraintViolationException exception) {
		Map<String, Object> errorDetails = new HashMap<>();
		exception.getConstraintViolations().forEach(violation -> {
			errorDetails.put(violation.getPropertyPath().toString(), violation.getMessage());
		});
		
		return ErrorResponse.of("request.constraint", "Invalid request constraint(s).", errorDetails);
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ErrorResponse handle(final MethodArgumentNotValidException exception) {
		List<String> errors = exception.getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.toList();
		
		return ErrorResponse.of("method.argument", "Invalid method argement(s).", errors);
	}
}
