package com.r2s.java_backend_06.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.java_backend_06.model.Profile;
import com.r2s.java_backend_06.repository.ProfileRepository;
import com.r2s.java_backend_06.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileRepository profileRepository;

	@PostMapping(path = "")
	public SuccessResponse<Profile> save(@RequestBody Profile profile) {
		profile.getUser().setProfile(profile);
		return SuccessResponse.of(this.profileRepository.save(profile));
	}

	@GetMapping(path = "")
	public SuccessResponse<List<Profile>> getAll() {
		return SuccessResponse.of(this.profileRepository.findAll());
	}
}
