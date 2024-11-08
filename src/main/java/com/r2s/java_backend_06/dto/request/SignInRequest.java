package com.r2s.java_backend_06.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
	private String userName;
	private String password;
}
