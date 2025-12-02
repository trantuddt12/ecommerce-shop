package com.ttl.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ttl.core.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUsername(String pUsername);
	
	@Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
	Optional<User> findByUsernameWithRoles(@Param("username") String pUsername);

	User findByEmail(String email);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
	User findUserById(@Param("id") String Id);
	
	@EntityGraph(attributePaths = {"roles"})
	List<User> findAll();

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.status = 'A' WHERE u.email = :email")
	int updateState(@Param("email") String pEmail);
}