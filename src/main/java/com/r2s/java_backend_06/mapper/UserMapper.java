package com.r2s.java_backend_06.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.r2s.java_backend_06.dto.request.SignUpRequest;
import com.r2s.java_backend_06.dto.request.UserRequestDTO;
import com.r2s.java_backend_06.dto.response.UserResponseDTO;
import com.r2s.java_backend_06.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// user -> user response dto
	@Mapping(source = "profile.displayName", target = "displayName")
//	@Mapping....
	UserResponseDTO toDTO(final User user);

	@Mapping(source = "displayName", target = "profile.displayName")
	User toModel(final UserRequestDTO dto);
	
	@Mapping(source = "displayName", target = "profile.displayName")
	User toModel(final SignUpRequest dto);
}
