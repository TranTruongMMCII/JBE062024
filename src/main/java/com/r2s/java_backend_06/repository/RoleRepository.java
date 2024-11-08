package com.r2s.java_backend_06.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.java_backend_06.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	List<Role> findByName(final String name);
}
