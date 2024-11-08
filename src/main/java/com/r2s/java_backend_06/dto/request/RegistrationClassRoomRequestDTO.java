package com.r2s.java_backend_06.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationClassRoomRequestDTO {
	private Integer classId;
	private Integer userId;
}
