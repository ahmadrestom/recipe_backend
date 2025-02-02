package com.application.Recipe.Models;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, unique=true)
	private user user;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="message", nullable=false)
	private String message;
	
	@Column(name = "is_read",nullable=false)
	private boolean isRead = false;
	
	@Column(name = "created_at",nullable = false, updatable=false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public Notification(UUID userId, String title, String message){
        this.user = new user();
        this.user.setId(userId);
        this.title = title;
        this.message = message;
    }
}