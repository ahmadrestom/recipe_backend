package com.application.Recipe.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.DTO.GETRecipeDTO;
import com.application.Recipe.DTO.POSTRecipeDTO;
import com.application.Recipe.DTO.categoryDTO_forRecipeGET;
import com.application.Recipe.DTO.chefDTO_forRecipeGET;
import com.application.Recipe.Models.Recipe;
import com.application.Recipe.Services.RecipeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/recipe")
public class recipeController {
	
	@Autowired
	RecipeService recipeService;
	
	@DeleteMapping("/deleteRecipeById/{recipeId}")
	public ResponseEntity<?> deleteRecipeById(@PathVariable Integer recipeId)
	{
		if(recipeService.deleteRecipeById(recipeId))
			return ResponseEntity.ok("Recipe deleted successfully");
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found or could not be deleted");
	}
	
	@DeleteMapping("/deleteRecipeByName/{recipeName}")
	public ResponseEntity<?> deleteByRecipeName(@PathVariable String recipeName)
	{
		if(recipeService.deleteRecipeByName(recipeName))
			return ResponseEntity.ok("Recipe deleted successfully");
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found or could not be deleted");
			
	}
	
	@GetMapping("/getRecipeByName/{recipeName}")
	public ResponseEntity<?> getRecipeByName(@PathVariable String recipeName)
	{
		Recipe recipe = recipeService.getRecipeByName(recipeName);
		if(recipe!=null)
		{
			GETRecipeDTO RecipeToBeReturned =  recipeService.convertToGETRecipeDTO(recipe);
			return ResponseEntity.ok(RecipeToBeReturned);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/getRecipeById/{recipeId}")
	public ResponseEntity<?> getRecipeById(@PathVariable Integer recipeId)
	{
		Optional<Recipe> recipeOpt = recipeService.getRecipeById(recipeId);
		if(recipeOpt.isPresent()) {
			Recipe recipe = recipeOpt.get();
			GETRecipeDTO RecipeToBeReturned =  recipeService.convertToGETRecipeDTO(recipe);
			return ResponseEntity.ok(RecipeToBeReturned);
		}
		else 
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/getRecentRecipes")
	public ResponseEntity<?> getRecentRecipes(@RequestParam(required = false) LocalDateTime time)
	{
		if (time == null) {
            time = LocalDateTime.now();
        }
		List<Recipe> recipes = recipeService.getLatestRecipes(time);
		if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GETRecipeDTO> recipeDTOs = recipeService.convertToGETRecipeDTOs(recipes);
            return ResponseEntity.ok(recipeDTOs);
        }
	}

	@GetMapping("/getAllRecipes")
	public ResponseEntity<?> getAllRecipes()
	{
		try {
			List<Recipe> recipes = recipeService.getAllRecipes();
			List<GETRecipeDTO> recipeDTOs = recipeService.convertToGETRecipeDTOs(recipes);
			return ResponseEntity.ok(recipeDTOs);
		}catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());	
		}
	}
	
	@PostMapping("/createRecipe")
	public ResponseEntity<?> createRecipe(@Valid @RequestBody POSTRecipeDTO pOSTRecipeDTO)
	{
		Recipe createdRecipe = recipeService.addRecipe(pOSTRecipeDTO);
		GETRecipeDTO RecipeToBeReturned = recipeService.convertToGETRecipeDTO(createdRecipe); 
		return ResponseEntity.status(HttpStatus.CREATED).body(RecipeToBeReturned);
	}
	
	
}
