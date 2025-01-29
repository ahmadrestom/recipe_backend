package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.Recipe.Models.Notification;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    
	List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);
	
}