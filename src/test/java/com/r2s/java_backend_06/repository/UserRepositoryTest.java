package com.r2s.java_backend_06.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.r2s.java_backend_06.model.User;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private static final String USER_NAME = "user01Test";
	private final User user01 = User.builder().userName(USER_NAME).age(-1).build();

	@BeforeAll
	void init() {
		this.userRepository.save(user01);
	}

	@AfterAll
	void tearDown() {
		this.userRepository.deleteByUserNameIn(List.of(USER_NAME));
	}

	@Test
	void testFindByUserName() {
		// given

		// when
		Optional<User> optUser = this.userRepository.findByUserName(USER_NAME);

		// then
		assertTrue(optUser.isPresent());
		User user = optUser.get();
		assertEquals(USER_NAME, user.getUserName());
		assertEquals(-1, user.getAge());
	}

	@Test
	void testFindByUserNameNotFound() {
		// given

		// when
		Optional<User> optUser = this.userRepository.findByUserName(USER_NAME + "111");

		// then
		assertTrue(optUser.isEmpty());
	}
}
