package com.r2s.java_backend_06.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.r2s.java_backend_06.exception.UserNotFoundException;
import com.r2s.java_backend_06.model.User;
import com.r2s.java_backend_06.repository.RoleRepository;
import com.r2s.java_backend_06.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private RoleRepository roleRepository;

	private static final String USER_NAME = "user01Test";
	private static final Integer USER_ID = -1;
	private final User user01 = User.builder().userName(USER_NAME).id(USER_ID).build();

	@Test
	void testFindByIdSuccess() {
		// given
		when(this.userRepository.findById(eq(USER_ID))).thenReturn(Optional.of(user01));
		
		// when
		User givenUser = this.userService.findById(USER_ID);
		
		// then
		assertNotNull(givenUser);
		assertEquals(USER_ID, givenUser.getId());
		assertEquals(USER_NAME, givenUser.getUserName());
		verify(this.userRepository, times(1)).findById(USER_ID);
	}

	@Test
	void testFindByIdNotFound() {
		// given
		when(this.userRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		// when
		Executable result = ()-> this.userService.findById(USER_ID);
		
		// then
		UserNotFoundException e = assertThrows(UserNotFoundException.class, result);
		assertEquals("notfound.user", e.getCode());
		assertEquals("User -1 was not found", e.getMessage());
		verify(this.userRepository, times(1)).findById(USER_ID);
	}
}
