package com.application.Recipe.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.application.Recipe.Enums.DifficultyLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="recipe")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="recipe_id", nullable = false)
	private Integer recipeId;
	
	@Column(name="recipe_name", nullable = false)
	private String recipeName;
	
	@Column(name="description", nullable = false)
	private String description;
	
	@Column(name="time_uploaded", nullable = false)
	private LocalDateTime timeUploaded;
	
	@Column(name="preparation_time", nullable = false)
	private Integer preparationTime;
	
	@Column(name="cooking_time", nullable = false)
	private Integer cookingTime;
	
	@Column(name="difficulty", nullable = false)
	@Enumerated(EnumType.STRING)
	private DifficultyLevel difficultyLevel;
	
	@Column(name="rating", nullable = false)
	private double rating;
	
	@Column(name="image_url", nullable = false)
	private String imageUrl;
	
	@Column(name="plate_image_url", nullable = false)
	private String plateImageUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id",  nullable=false)
	@JsonManagedReference
	private category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chef_id",  nullable=false)
	@JsonManagedReference
	private chef chef;
	
	@PrePersist
    protected void onCreate() {
        timeUploaded = LocalDateTime.now();
    }
}
