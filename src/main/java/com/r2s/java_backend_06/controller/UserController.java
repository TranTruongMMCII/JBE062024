package com.r2s.java_backend_06.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.java_backend_06.dto.request.UserRequestDTO;
import com.r2s.java_backend_06.dto.response.UserResponseDTO;
import com.r2s.java_backend_06.mapper.UserMapper;
import com.r2s.java_backend_06.model.User;
import com.r2s.java_backend_06.response.SuccessResponse;
import com.r2s.java_backend_06.service.UserService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

//	@GetMapping(path = "") // -> /users
//	public String sayHello() {
//		return "Hello, World!";
//	}

	/**
	 * Save the given user
	 * 
	 * @param user given user
	 * @return the saved user
	 */
	@PostMapping(path = "")
	@Operation(summary = "Save a new user!")
	public SuccessResponse<UserResponseDTO> save(@RequestBody @Validated @Valid UserRequestDTO dto) {
		User user = this.userMapper.toModel(dto);
		User savedUser = this.userService.save(user);
		return SuccessResponse.of(this.userMapper.toDTO(savedUser));
	}

	@GetMapping(path = "")
//	@PreAuthorize(value = "hasAnyAuthority('ADMIN')")
	@Deprecated(since = "2024.11.08")
	public SuccessResponse<List<UserResponseDTO>> getAll(Pageable pageable,
			@RequestParam(name = "sorts", required = false) String sorts) {
//		log.info("page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
		
		log.error("current user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
		
		// tao ra object pagerequest
		PageRequest pageRequest = null;

		if (StringUtils.hasText(sorts)) { // neu co sorts
			String[] sortArr = sorts.split(",");
			if (sortArr.length % 2 != 0) {
				// throw exception
			}

			List<Order> orders = new ArrayList<>();
			for (int i = 0; i < sortArr.length; i += 2) {
				orders.add(new Order(getDirection(sortArr[i + 1]), sortArr[i]));
			}

			Sort sort = Sort.by(orders);
			pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		} else {
			pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		}

		List<User> result = this.userService.getAll(pageRequest);
		// x -> this.userMapper.toDTO(x)
		List<UserResponseDTO> response = result.stream().map(this.userMapper::toDTO).toList();
		return SuccessResponse.of(response);
	}

	private static Direction getDirection(final String direction) {
		if (Direction.ASC.toString().equals(direction)) {
			return Direction.ASC;
		}

		return Direction.DESC;
	}

	@GetMapping(path = "/searchById")
	public SuccessResponse<User> searchById(@RequestParam(name = "id") int id) {
		return SuccessResponse.of(this.userService.findById(id));
	}

	@GetMapping(path = "/searchByName")
	public List<User> searchByName(@RequestParam(name = "name") String name) {
		return this.userService.findByName(name);
	}

	@GetMapping(path = "/searchByNameContains")
	public List<User> searchByNameContains(@RequestParam(name = "name") String name) {
		return this.userService.findByNameContains(name);
	}

	@GetMapping(path = "/searchByFilterNameAndIsDeleted")
	public List<User> searchByFilterNameAndIsDeleted(@RequestParam(name = "name") String name,
			@RequestParam(name = "isDeleted") Boolean isDeleted) {
		return this.userService.findByNameContainsAndIsDeleted(name, isDeleted);
	}

	@PutMapping(path = "")
	public User update(@RequestBody User newUser) {
		return this.userService.update(newUser);
	}

	@DeleteMapping(path = "/softDelete")
	public Boolean delete(@RequestBody User deletedUser) {
		return this.userService.delete(deletedUser);
	}

	@DeleteMapping(path = "/hardDelete")
	@Hidden
	public Boolean delete1(@RequestBody User deletedUser) {
		return this.userService.delete1(deletedUser);
	}
}
