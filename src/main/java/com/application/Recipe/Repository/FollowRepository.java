package com.application.Recipe.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.Recipe.CompositeKeys.FollowId;
import com.application.Recipe.Models.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId>{
	
	boolean existsByFollowId_UserIdAndFollowId_ChefId(UUID userId, UUID chefId);
}
