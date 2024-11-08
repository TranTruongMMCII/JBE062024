package com.r2s.java_backend_06.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.r2s.java_backend_06.model.User;

public interface UserService {
	User save(User user);

	List<User> getAll(Pageable pageable);

	User findById(int id);

	User update(User newUser);

	Boolean delete(User deletedUser);

	Boolean delete1(User deletedUser);

	List<User> findByName(String name);

	List<User> findByNameContains(String name);

	List<User> findByNameContainsAndIsDeleted(String name, Boolean isDeleted);

	Boolean signUp(User user);
}
