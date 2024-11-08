package com.r2s.java_backend_06.controller;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.java_backend_06.dto.request.SignInRequest;
import com.r2s.java_backend_06.dto.request.SignUpRequest;
import com.r2s.java_backend_06.dto.response.SignInResponse;
import com.r2s.java_backend_06.mapper.UserMapper;
import com.r2s.java_backend_06.model.User;
import com.r2s.java_backend_06.response.SuccessResponse;
import com.r2s.java_backend_06.security.CustomUserDetails;
import com.r2s.java_backend_06.service.UserService;
import com.r2s.java_backend_06.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

	private final UserMapper userMapper;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	@PostMapping(path = "/signUp")
	public SuccessResponse<Boolean> signUp(@RequestBody SignUpRequest request) {
		User user = this.userMapper.toModel(request);
//		log.info("{}", user.getUserName());
		return SuccessResponse.of(this.userService.signUp(user));
	}
	
	@PostMapping(path = "/signIn")
	public SuccessResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
		Authentication authentication = 
				this.authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

        // Retrieve user details from the authenticated token
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Generate JWT token
        String accessToken = jwtUtils.generateToken(userDetails);
        Date expriedDate = jwtUtils.extractExpiration(accessToken);

        return SuccessResponse.of(SignInResponse.builder()
        		.token(accessToken)
        		.expiredDate(expriedDate)
        		.build());
	}
}
