package com.r2s.java_backend_06.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationClassRoomResponseDTO {
	private String name;
	private List<UserResponseDTO> users;
}
