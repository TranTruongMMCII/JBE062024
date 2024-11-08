package com.r2s.java_backend_06.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private String userName;
	private String email;
	private Integer age;
	private Boolean isDeleted;
	private String displayName;
}
