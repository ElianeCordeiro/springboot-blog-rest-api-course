package com.springboot.blog.springbootblogrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.springboot.blog.springbootblogrestapi.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	Optional<User> findByUsername(String name);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}
