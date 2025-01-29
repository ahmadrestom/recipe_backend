package com.application.Recipe.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Recipe.Models.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {

    // Custom query to find the token by userId
    Optional<UserToken> findByUserId(UUID userId);

    // Custom query to delete the token by userId
    void deleteByUserId(UUID userId);
    public UserToken findByToken(String token);
}
