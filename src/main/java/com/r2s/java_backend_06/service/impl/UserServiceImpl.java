package com.r2s.java_backend_06.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.r2s.java_backend_06.constant.SecurityRole;
import com.r2s.java_backend_06.exception.UserAlreadyExistException;
import com.r2s.java_backend_06.exception.UserNotFoundException;
import com.r2s.java_backend_06.model.User;
import com.r2s.java_backend_06.repository.RoleRepository;
import com.r2s.java_backend_06.repository.UserRepository;
import com.r2s.java_backend_06.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;

	@Override
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public List<User> getAll(Pageable pageable) {
		return this.userRepository.findAll(pageable).stream().toList();
	}

	@Override
	public User findById(int id) {
		Optional<User> optional = this.userRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}

		throw new UserNotFoundException("notfound.user", "User %d was not found".formatted(id));
	}

	@Override
	public User update(User newUser) {
		// tim user update co ton tai khong
		Optional<User> optionalUser = this.userRepository.findById(newUser.getId());
		if (optionalUser.isEmpty()) {
			throw new RuntimeException("user k co ton tai!");
		}

		User updatedUser = optionalUser.get();
		updatedUser.setUserName(newUser.getUserName());
		updatedUser.setEmail(newUser.getEmail());
		return this.userRepository.save(updatedUser);
	}

	@Override
	public Boolean delete(User deletedUser) {
		// tim user update co ton tai khong
		User updatedUser = this.userRepository.findById(deletedUser.getId())
				.orElseThrow(() -> new UserNotFoundException("notfound.user",
						"User %d was not found".formatted(deletedUser.getId())));
		updatedUser.setIsDeleted(true);
		updatedUser = this.userRepository.save(updatedUser);
		return updatedUser.getIsDeleted();
	}

	@Override
	public Boolean delete1(User deletedUser) {
		// tim user update co ton tai khong
		Optional<User> optionalUser = this.userRepository.findById(deletedUser.getId());
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("notfound.user", "User %d was not found".formatted(deletedUser.getId()));
		}

		// style 1
//		this.userRepository.deleteById(deletedUser.getId());

		// style 2
		User updatedUser = optionalUser.get();
		this.userRepository.delete(updatedUser);
		return true;
	}

	@Override
	public List<User> findByName(String name) {
//		return this.userRepository.findByUserName(name);
		return List.of();
	}

	@Override
	public List<User> findByNameContains(String name) {
//		return this.userRepository.findByNameContains(name);
//		return this.userRepository.findByNameContaining(name);
		return this.userRepository.findByNameContainsByRegex("%" + name + "%");
	}

	@Override
	public List<User> findByNameContainsAndIsDeleted(String name, Boolean isDeleted) {
		return this.userRepository.findByUserNameContainsAndIsDeleted(name, isDeleted);
	}

	@Override
	public Boolean signUp(User user) {
		// check exists userName
		this.userRepository.findByUserName(user.getUserName())
		.ifPresent((u) -> {
			throw new UserAlreadyExistException("auth.signup", "User with userName=%s already existed!".formatted(u.getUserName()));
		});
		
		user.setIsDeleted(false);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setRoles(this.roleRepository.findByName(SecurityRole.ROLE_USER));
		this.userRepository.save(user);
		return true;
	}

}
