package com.r2s.java_backend_06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.java_backend_06.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
