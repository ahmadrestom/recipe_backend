package com.application.Recipe.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.application.Recipe.DTO.GETRecipeDTO;
import com.application.Recipe.DTO.POSTRecipeDTO;
import com.application.Recipe.Models.Recipe;

import jakarta.transaction.Transactional;

public interface RecipeService {
	
	@Transactional
	public Recipe addRecipe(POSTRecipeDTO pOSTRecipeDTO);
	
	public Optional<Recipe> getRecipeById(Integer recipeId);
	
	public Recipe getRecipeByName(String recipeName);
	
	public List<Recipe> getAllRecipes();
	
	public List<Recipe> getLatestRecipes(LocalDateTime time);
	
	@Transactional
	public boolean deleteRecipeById(Integer recipeId);
	
	@Transactional
	public boolean deleteRecipeByName(String recipeName);
	
	public GETRecipeDTO convertToGETRecipeDTO(Recipe recipe);
	
	public List<GETRecipeDTO> convertToGETRecipeDTOs(List<Recipe> recipes);
}
