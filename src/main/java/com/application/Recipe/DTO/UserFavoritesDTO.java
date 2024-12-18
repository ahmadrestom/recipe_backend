package com.application.Recipe.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.application.Recipe.Enums.DifficultyLevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserFavoritesDTO {
	private UUID recipeId;
	private String recipeName;
	private String description;
	private LocalDateTime timeUploaded;
	private Integer preparationTime;
	private Integer cookingTime;
	private DifficultyLevel difficultyLevel;
	private double rating;
	private String imageUrl;
	private String categoryName;
	private String chefName;
	private String chefPictureUrl;

}
