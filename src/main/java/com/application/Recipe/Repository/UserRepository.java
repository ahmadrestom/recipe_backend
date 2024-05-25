package com.application.Recipe.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.application.Recipe.Models.user;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<user ,Integer>{
	Optional<user> findByEmail(String email);
	void deleteByEmail(String email);
	
	@Transactional
    @Modifying
    @Query(value = "INSERT INTO chef (user_id, location, phone_number, bio, years_experience) " +
            "VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void upgradeUserToChef(Integer userId, String location, String phoneNumber, String bio, Integer yearsExperience);
}
