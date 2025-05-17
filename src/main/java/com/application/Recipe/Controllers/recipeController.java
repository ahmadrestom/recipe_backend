package com.application.Recipe.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
import com.application.Recipe.DTO.GETRecipeDTOProfile;
import com.application.Recipe.DTO.IngredientDTO;
import com.application.Recipe.DTO.InstructionDTO;
import com.application.Recipe.DTO.POSTRecipeDTO;
import com.application.Recipe.Models.Recipe;
import com.application.Recipe.Services.RecipeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/recipe")
public class recipeController {
	
	@Autowired
	RecipeService recipeService;
	
	@GetMapping("/getRecipeByChefId/{chefId}")
	public ResponseEntity<?> getChefRecipes(@PathVariable UUID chefId){
		List<GETRecipeDTOProfile> recipes = recipeService.getRecipeByChefId(chefId);
		if(recipes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No recipes for the chef with id: "+chefId);
		}else if(!recipes.isEmpty()) {
			return ResponseEntity.ok(recipes);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();			
		}
	}
	
	@GetMapping("/getRecipesByCategoryName/{categoryName}")
	public ResponseEntity<?> getRecipesByCategory(@PathVariable String categoryName){
		List<GETRecipeDTO> recipes = recipeService.getRecipesByCategoryName(categoryName);
		return ResponseEntity.ok(recipes);
	}
	
	@DeleteMapping("/deleteRecipeById/{recipeId}")
	public ResponseEntity<?> deleteRecipeById(@PathVariable UUID recipeId)
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
	public ResponseEntity<?> getRecipeById(@PathVariable UUID recipeId)
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
	
	@GetMapping("/getAllRecipes")
	public ResponseEntity<?> getAllRecipes(@RequestParam(required = false) Integer page,
	        @RequestParam(required = false) Integer size)
	{
		try {
			List<Recipe> recipes = recipeService.getAllRecipes(page,size);
			List<GETRecipeDTO> recipeDTOs = recipeService.convertToGETRecipeDTOs(recipes);
			return ResponseEntity.ok(recipeDTOs);
		}catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());	
		}
	}
	
	@GetMapping("/getRecentRecipes")
	public ResponseEntity<?> getRecentRecipes(@RequestParam(required = false) LocalDateTime time , @RequestParam(required = false) Integer page,
	        @RequestParam(required = false) Integer size)
	{
		if (time == null) {
            time = LocalDateTime.now();
        }
		List<Recipe> recipes = recipeService.getLatestRecipes(time, page, size);
		if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GETRecipeDTO> recipeDTOs = recipeService.convertToGETRecipeDTOs(recipes);
            return ResponseEntity.ok(recipeDTOs);
        }
	}
	
	@GetMapping("/getRecipeIngredients/{recipeName}")
	public ResponseEntity<?> getRecipeIngredients(@PathVariable String recipeName){
		try {
			Set<IngredientDTO> ingredients = recipeService.GetRecipeIngredients(recipeName);
			return ResponseEntity.ok(ingredients);		
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/getRecipeInstructions/{recipeName}")
	public ResponseEntity<?> getRecipeInstructions(@PathVariable String recipeName){
		try {
			List<InstructionDTO> instructions = recipeService.getRecipeInstructions(recipeName);
			return ResponseEntity.ok(instructions);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
