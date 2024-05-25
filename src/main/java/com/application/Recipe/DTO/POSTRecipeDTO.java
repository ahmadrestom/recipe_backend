package com.application.Recipe.DTO;

import java.time.LocalDateTime;

import com.application.Recipe.Enums.DifficultyLevel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POSTRecipeDTO{
	
	@NotEmpty(message = "Recipe name is required")
	private String recipeName;
	@NotEmpty(message = "Recipe name is required")
	private String description;
	private LocalDateTime timeUploaded;
	@NotNull(message = "Preparation time is required")
	private Integer preparationTime;
	@NotNull(message = "Cooking time is required")
	private Integer cookingTime;
	@NotEmpty(message = "Difficulty level is required")
	private DifficultyLevel difficultyLevel;
	@NotNull(message = "Rating is required")
	private double rating;
	@NotEmpty(message = "Image URL is required")
	private String imageUrl;
	@NotEmpty(message = "Plate image URL is required")
	private String plateImageUrl;
	@NotNull(message = "Category ID is required")
	private Integer categoryId;
	@NotNull(message = "Chef ID is required")
	private Integer chefId;
}
