package com.r2s.java_backend_06.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.r2s.java_backend_06.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String name);
	
	@Transactional
	void deleteByUserNameIn(List<String> names);
	
//	List<User> findByUserName(String name);

	List<User> findByUserNameContains(String name);

	List<User> findByUserNameContaining(String name);

	List<User> findByUserNameContainsAndIsDeleted(String name, Boolean isDeleted);

	@Query(nativeQuery = true, value = "select * from jbe062024.user where user_name like ?1")
	List<User> findByNameContainsByRegex(String name);
}
