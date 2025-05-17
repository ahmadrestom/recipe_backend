package com.application.Recipe.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.application.Recipe.DTO.GETRecipeDTO;
import com.application.Recipe.DTO.GETRecipeDTOProfile;
import com.application.Recipe.DTO.IngredientDTO;
import com.application.Recipe.DTO.InstructionDTO;
import com.application.Recipe.DTO.POSTRecipeDTO;
import com.application.Recipe.Models.Recipe;

import jakarta.transaction.Transactional;

public interface RecipeService {
	
	@Transactional
	public Recipe addRecipe(POSTRecipeDTO pOSTRecipeDTO);
	
	public Optional<Recipe> getRecipeById(UUID recipeId);
	
	public Recipe getRecipeByName(String recipeName);
	
	public List<Recipe> getAllRecipes();
	
	public List<Recipe> getLatestRecipes(LocalDateTime time);
	
	public List<GETRecipeDTO> getRecipesByCategoryName(String categoryName);
	
	@Transactional
	public boolean deleteRecipeById(UUID recipeId);
	
	@Transactional
	public boolean deleteRecipeByName(String recipeName);
	
	public GETRecipeDTO convertToGETRecipeDTO(Recipe recipe);
	
	public List<GETRecipeDTO> convertToGETRecipeDTOs(List<Recipe> recipes);
	
	public	Set<IngredientDTO> GetRecipeIngredients(String recipeName);
	
	public List<InstructionDTO> getRecipeInstructions(String recipeName);
	
	public List<GETRecipeDTOProfile> getRecipeByChefId(UUID chefId);
}
