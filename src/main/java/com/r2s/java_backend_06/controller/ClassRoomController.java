package com.r2s.java_backend_06.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.java_backend_06.dto.request.RegistrationClassRoomRequestDTO;
import com.r2s.java_backend_06.exception.ClassRoomNotFoundException;
import com.r2s.java_backend_06.model.ClassRoom;
import com.r2s.java_backend_06.model.User;
import com.r2s.java_backend_06.repository.ClassRoomRepository;
import com.r2s.java_backend_06.response.SuccessResponse;
import com.r2s.java_backend_06.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/classrooms")
@RequiredArgsConstructor
@Slf4j
public class ClassRoomController {

	private final ClassRoomRepository classRoomRepository;
	private final UserService userService;

	@PostMapping(path = "")
	public SuccessResponse<Boolean> save(@RequestBody RegistrationClassRoomRequestDTO dto) {
		// lay ra class -> kiem tra tra xem classroom co ton tai khong
		ClassRoom classRoom = this.classRoomRepository.findById(dto.getClassId())
				.orElseThrow(() -> new ClassRoomNotFoundException());
//		classRoom.getUsers().forEach(x->System.out.println(x));;
		
		// kiem tra xem user co ton tai khong
//		User user = this.userService.findById(dto.getUserId());
//		
////		// add user vao classroom
////		user.setClassRoom(classRoom);
////		classRoom.getUsers().add(user);
////		
////		// luu ben 1
////		this.classRoomRepository.save(classRoom);
//		
//		// luu ben nhieu
//		user.setClassRoom(classRoom);
//		this.userService.save(user);
		
		return SuccessResponse.of(true);
	}
}
