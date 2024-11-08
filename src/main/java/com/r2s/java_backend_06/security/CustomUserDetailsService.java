package com.r2s.java_backend_06.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.r2s.java_backend_06.model.User;
import com.r2s.java_backend_06.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("Cannot find user with userName=" + username));
		return new CustomUserDetails(user);
	}

}
