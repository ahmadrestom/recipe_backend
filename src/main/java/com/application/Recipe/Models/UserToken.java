package com.application.Recipe.Models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_token")
public class UserToken {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable=false, updatable=false)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, unique=true)
    private user user;
	
	@Column(name="device_token")
    private String token;
    
    @CreationTimestamp
    @Column(name="created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;
    
    public UserToken(UUID userId, String token) {
    	this.user = new user();
        this.user.setId(userId);
        this.token = token;
    }
}
