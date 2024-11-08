package com.r2s.java_backend_06.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
	@NotBlank
	private String userName;

	@NotNull
	private String email;

	@NotNull
	@Min(value = 17L, message = "Age must be greater than 17.")
	@Max(value = 101L, message = "Age must be less than 101.")
	private Integer age;

	private String displayName;
}
