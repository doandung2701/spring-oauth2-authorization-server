package com.buidoandung.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buidoandung.authorizationserver.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	@Query("SELECT DISTINCT u FROM User u WHERE u.userName = :username")
	User findByUsername(@Param("username") String username);
}
