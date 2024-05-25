package com.application.Recipe.ServiceImplementation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.DTO.GETRecipeDTO;
import com.application.Recipe.DTO.POSTRecipeDTO;
import com.application.Recipe.DTO.categoryDTO_forRecipeGET;
import com.application.Recipe.DTO.chefDTO_forRecipeGET;
import com.application.Recipe.Models.category;
import com.application.Recipe.Models.chef;
import com.application.Recipe.Models.Recipe;
import com.application.Recipe.Repository.CategoryRepository;
import com.application.Recipe.Repository.ChefRepository;
import com.application.Recipe.Repository.RecipeRepository;
import com.application.Recipe.Services.RecipeService;

import jakarta.transaction.Transactional;

@Service
public class RecipeServiceImplementation implements RecipeService{

	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ChefRepository chefRepository;

	@Override
	public Optional<Recipe> getRecipeById(Integer recipeId) {
		return recipeRepository.findById(recipeId);
	}
	
	@Override
	public Recipe getRecipeByName(String recipeName) {
		return recipeRepository.findByRecipeName(recipeName).orElseThrow(
				()->new RuntimeException("Recipe not found")
				);
	}
	
	@Override
	public List<Recipe> getAllRecipes(){
		List<Recipe> rs = recipeRepository.findAll();
		if(rs.isEmpty())
			throw new RuntimeException("No recipes found");
		return rs;
	}
	
	@Override
	public List<Recipe> getLatestRecipes(LocalDateTime time) {
		LocalDateTime cutoffTime = time.minus(2, ChronoUnit.WEEKS);
		return recipeRepository.findRecentRecipes(cutoffTime);
	}
	
	@Override
	public List<GETRecipeDTO> convertToGETRecipeDTOs(List<Recipe> recipes) {
        return recipes.stream().map(this::convertToGETRecipeDTO).collect(Collectors.toList());
    }
	
	@Override
	public GETRecipeDTO convertToGETRecipeDTO(Recipe recipe) {
		
		categoryDTO_forRecipeGET categoryDTO = new categoryDTO_forRecipeGET
			(
				 recipe.getCategory().getCategory_id(),
				 recipe.getCategory().getCategory_name()
			);
		
		
		chefDTO_forRecipeGET chefDTO = new chefDTO_forRecipeGET
				(
						recipe.getChef().getId(),
						recipe.getChef().getFirstName(), 
						recipe.getChef().getLastName(), 
						recipe.getChef().getImage_url(), 
						recipe.getChef().getLocation(),
						recipe.getChef().getPhone_number(), 
						recipe.getChef().getBio(), 
						recipe.getChef().getYears_experience()
				);
		
		GETRecipeDTO RecipeGetDTO = GETRecipeDTO.builder()
				.recipeId(recipe.getRecipeId())
				.recipeName(recipe.getRecipeName())
				.description(recipe.getDescription())
				.timeUploaded(recipe.getTimeUploaded())
				.preparationTime(recipe.getPreparationTime())
				.cookingTime(recipe.getCookingTime())
				.difficultyLevel(recipe.getDifficultyLevel())
				.rating(recipe.getRating())
				.imageUrl(recipe.getImageUrl())
				.plateImageUrl(recipe.getPlateImageUrl())
				.category(categoryDTO)
				.chef(chefDTO)
				.build();
		
		return RecipeGetDTO;
	}
	
	@Override
	public Recipe addRecipe(POSTRecipeDTO pOSTRecipeDTO) {
		if(recipeRepository.findByRecipeName(pOSTRecipeDTO.getRecipeName()).isPresent())
		{
			throw new RuntimeException("Recipe already exists");
		}
		
		category category = categoryRepository.findById(pOSTRecipeDTO.getCategoryId())
				.orElseThrow(()->new RuntimeException("Category Not Found"));
		
		chef chef = chefRepository.findById(pOSTRecipeDTO.getChefId())
				.orElseThrow(()->new RuntimeException("Chef Not Found"));
		
		Recipe r = Recipe.builder()
				.recipeName(pOSTRecipeDTO.getRecipeName())
				.description(pOSTRecipeDTO.getDescription())
				.timeUploaded(pOSTRecipeDTO.getTimeUploaded())
				.preparationTime(pOSTRecipeDTO.getPreparationTime())
				.cookingTime(pOSTRecipeDTO.getCookingTime())
				.difficultyLevel(pOSTRecipeDTO.getDifficultyLevel())
				.rating(pOSTRecipeDTO.getRating())
				.imageUrl(pOSTRecipeDTO.getImageUrl())
				.plateImageUrl(pOSTRecipeDTO.getPlateImageUrl())
				.category(category)
				.chef(chef)
				.build();
		
		return recipeRepository.save(r);
	}

	@Override
	public boolean deleteRecipeById(Integer recipeId) {
		if(recipeRepository.existsById(recipeId)) {
			recipeRepository.deleteById(recipeId);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteRecipeByName(String recipeName) {
		Optional<Recipe> recipe = recipeRepository.findByRecipeName(recipeName);
		if(recipe.isPresent()) {
			recipeRepository.deleteByRecipeName(recipeName);
			return true;
		}
		return false;
	}

}