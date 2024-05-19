package com.application.Recipe.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.application.Recipe.Models.user;


@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<user ,Integer>{
	Optional<user> findByEmail(String email);
	void deleteByEmail(String email);
}
