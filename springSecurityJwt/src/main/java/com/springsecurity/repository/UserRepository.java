package com.springsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.entities.Role;
import com.springsecurity.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User>findByEmail(String email);
	
	
	User findByRole(Role role);
	

}
