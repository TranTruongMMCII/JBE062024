package com.r2s.java_backend_06.dto.response;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {

	private String token;
	private Date expiredDate;
}
