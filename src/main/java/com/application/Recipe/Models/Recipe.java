package com.application.Recipe.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.application.Recipe.Enums.DifficultyLevel;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="recipe")
@Getter
@Setter
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="recipe_id", nullable = false)
	private UUID recipeId;
	
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
	
	@OneToOne(mappedBy = "recipe", fetch = FetchType.LAZY)
    private NutritionInformation nutritionInformation;
	
	@ManyToMany(mappedBy = "favorites")
	private Set<user> favoritesByUsers;
	
	@OneToMany(mappedBy="recipe",  fetch=FetchType.LAZY)
	private Set<ingredient> ingredients;
	
	@OneToMany(mappedBy="recipe", fetch=FetchType.LAZY)
	private List<Instruction> instructions;
	
	@OneToMany(mappedBy="recipe", fetch=FetchType.LAZY)
	private Set<Review> reviews;
	
	@PrePersist
    protected void onCreate() {
        timeUploaded = LocalDateTime.now();
    }
}
