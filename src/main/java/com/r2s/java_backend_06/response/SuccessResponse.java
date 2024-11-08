package com.r2s.java_backend_06.response;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuccessResponse<T> {
	private final ResponseStatus status = ResponseStatus.SUCCESS;
	
	@JsonFormat(timezone = "UTC")
	private final Date timestamp = new Date();

	private T content;
	private String code;
	private String message;
	private Integer size;

	public static <T> SuccessResponse<T> of(T content) {
		return SuccessResponse.<T>builder()
				.content(content).code("success").message("success")
				.size(content != null && content instanceof Collection<?> ? ((Collection<?>) content).size() : 0)
				.build();
	}
}
