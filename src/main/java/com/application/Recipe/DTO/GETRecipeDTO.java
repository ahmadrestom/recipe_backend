package com.application.Recipe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.application.Recipe.Enums.DifficultyLevel;
import com.application.Recipe.Models.Review;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GETRecipeDTO {
	
	private Integer recipeId;
	private String recipeName;
	private String description;
	private LocalDateTime timeUploaded;
	private Integer preparationTime;
	private Integer cookingTime;
	private DifficultyLevel difficultyLevel;
	private double rating;
	private String imageUrl;
	private String plateImageUrl;
	private categoryDTO_forRecipeGET category;
	private chefDTO_forRecipeGET chef;
	
	NutritionInformationDTOForRecipe ni;
	
	Set<IngredientDTO> ingredients;
	 
	List<InstructionDTO> instructions;
	
	Set<Review> reviews;
	
	
}
