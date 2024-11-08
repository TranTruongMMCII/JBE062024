package com.r2s.java_backend_06.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
	private final ResponseStatus status = ResponseStatus.FAILURE;

	@JsonFormat(timezone = "UTC")
	private final Date timestamp = new Date();
	private String code;
	private String message;
	private Map<String, Object> details;
	private List<String> errors;

	public static ErrorResponse of(String code, String message) {
		return ErrorResponse.builder()
				.code(code)
				.message(message)
				.build();
	}
	
	public static ErrorResponse of(String code, String message, Map<String, Object> details) {
		return ErrorResponse.builder()
				.code(code)
				.message(message)
				.details(details)
				.build();
	}
	
	public static ErrorResponse of(String code, String message, List<String> errors) {
		return ErrorResponse.builder()
				.code(code)
				.message(message)
				.errors(errors)
				.build();
	}
}
