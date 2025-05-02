package com.application.Recipe.ServiceImplementation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.FirebaseService;
import com.application.Recipe.CompositeKeys.IngredientId;
import com.application.Recipe.CompositeKeys.InstructionId;
import com.application.Recipe.DTO.GETRecipeDTO;
import com.application.Recipe.DTO.GETRecipeDTOProfile;
import com.application.Recipe.DTO.IngredientDTO;
import com.application.Recipe.DTO.InstructionDTO;
import com.application.Recipe.DTO.NutritionInformationDTOForRecipe;
import com.application.Recipe.DTO.POSTRecipeDTO;
import com.application.Recipe.DTO.ReviewUserData;
import com.application.Recipe.DTO.GetReviewDTO;
import com.application.Recipe.DTO.categoryDTO_forRecipeGET;
import com.application.Recipe.DTO.chefDTO_forRecipeGET;
import com.application.Recipe.Models.category;
import com.application.Recipe.Models.chef;
import com.application.Recipe.Models.ingredient;
import com.application.Recipe.Models.user;
import com.application.Recipe.Models.Instruction;
import com.application.Recipe.Models.Notification;
import com.application.Recipe.Models.NutritionInformation;
import com.application.Recipe.Models.Recipe;
import com.application.Recipe.Models.Review;
import com.application.Recipe.Models.UserToken;
import com.application.Recipe.Repository.CategoryRepository;
import com.application.Recipe.Repository.ChefRepository;
import com.application.Recipe.Repository.IngredientsRepository;
import com.application.Recipe.Repository.InstructionsRepository;
import com.application.Recipe.Repository.NotificationRepository;
import com.application.Recipe.Repository.NutritionInformationRepository;
import com.application.Recipe.Repository.RecipeRepository;
import com.application.Recipe.Repository.UserTokenRepository;
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
	
	@Autowired
	NutritionInformationRepository nutritionInformationRepository;

	@Autowired
	IngredientsRepository ingredientsRepository;
	
	@Autowired
	InstructionsRepository instructionRepository;
	
	@Autowired
	UserTokenRepository userTokenRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	FirebaseService firebaseService;
	
	public List<GETRecipeDTOProfile> getRecipeByChefId(UUID chefId){
		List<Recipe> recipes = recipeRepository.findAllByChefId(chefId);
		List<GETRecipeDTOProfile> recipeDTO = new ArrayList<>();
		recipes.forEach(e ->{
			GETRecipeDTOProfile recipe = GETRecipeDTOProfile.builder()
					.recipeId(e.getRecipeId())
					.recipeName(e.getRecipeName())
					.preparationTime(e.getPreparationTime())
					.rating(e.getRating())
					.imageUrl(e.getImageUrl())
					.build();
			recipeDTO.add(recipe);
		});
		return recipeDTO;
	}
	
	@Override
	public Optional<Recipe> getRecipeById(UUID recipeId) {
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
		//LocalDateTime cutoffTime = time.minus(2, ChronoUnit.DAYS);
		List<Recipe> recentRecipes = recipeRepository.findTop10ByOrderByTimeUploadedDesc();
		Collections.sort(recentRecipes, Comparator.comparing(Recipe::getTimeUploaded));
		return recentRecipes;
	}
	
	@Override
	public List<GETRecipeDTO> convertToGETRecipeDTOs(List<Recipe> recipes) {
        return recipes.stream().map(this::convertToGETRecipeDTO).collect(Collectors.toList());
    }
	
	@Override
	public GETRecipeDTO convertToGETRecipeDTO(Recipe recipe){
		
		categoryDTO_forRecipeGET categoryDTO = new categoryDTO_forRecipeGET
			(
				 recipe.getCategory().getCategory_id(),
				 recipe.getCategory().getCategory_name()
			);
		
		Set<IngredientDTO> ingredients = new HashSet<>();
		for(ingredient i: recipe.getIngredients()) {
			
			IngredientDTO dto = IngredientDTO.builder()
					.ingredientName(i.getId().getIngredient())
					.grams(i.getGrams())
					.build();
			
			ingredients.add(dto);
		}
		
		List<InstructionDTO> instructions = new ArrayList<>();
		for(Instruction i: recipe.getInstructions()) {
			InstructionDTO dto = InstructionDTO.builder()
					.instruction(i.getInstruction())
					.order(i.getId().getInstructionOrder())
					.build();
			instructions.add(dto);
		}
		
		Set<GetReviewDTO> reviews = new HashSet<>();
		for(Review r: recipe.getReviews()) {
			
			ReviewUserData u = ReviewUserData.builder()
					.firstName(r.getUser().getFirstName())
					.lastName(r.getUser().getLastName())
					.imageUrl(r.getUser().getImage_url())
					.userId(r.getUser().getId())
					.build();
			
			GetReviewDTO dto = GetReviewDTO.builder()
					.text(r.getText())
					//.likes(r.getLikes())
					//.dislikes(r.getDislikes())
					.timeUploaded(r.getTimeUploaded())
					.user(u)
					.build();
			reviews.add(dto);
		}
		
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
		
		NutritionInformationDTOForRecipe niDTO = NutritionInformationDTOForRecipe.builder()
				.calories(recipe.getNutritionInformation().getCalories())
				.total_fat(recipe.getNutritionInformation().getTotal_fat())
				.cholesterol(recipe.getNutritionInformation().getCholesterol())
				.carbohydrates(recipe.getNutritionInformation().getCarbohydrates())
				.protein(recipe.getNutritionInformation().getProtein())
				.sugar(recipe.getNutritionInformation().getSugar())
				.sodium(recipe.getNutritionInformation().getSodium())
				.fiber(recipe.getNutritionInformation().getFiber())
				.zinc(recipe.getNutritionInformation().getZinc())
				.magnesium(recipe.getNutritionInformation().getMagnesium())
				.potassium(recipe.getNutritionInformation().getPotassium())
				.build();
		
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
				.ni(niDTO)
				.instructions(instructions)
				.ingredients(ingredients)
				.reviews(reviews)
				.build();
		
		return RecipeGetDTO;
	}
	
	@Override
	public List<InstructionDTO> getRecipeInstructions(String recipeName){
		Recipe r = recipeRepository.findByRecipeName(recipeName)
				.orElseThrow(()->new RuntimeException("Recipe not available"));
		
		List<InstructionDTO> DTOs = new ArrayList<>();
		List<Instruction> instructions = r.getInstructions();
		if(instructions.isEmpty())
			throw new RuntimeException("No instructions found");
		for(Instruction i: instructions) {
			InstructionDTO dto = InstructionDTO.builder()
					.instruction(i.getInstruction())
					.order(i.getId().getInstructionOrder())
					.build();
			DTOs.add(dto);
		}
		return DTOs;
		
		
	}
	
	@Override
	public	Set<IngredientDTO> GetRecipeIngredients(String recipeName){
		
		Recipe r = recipeRepository.findByRecipeName(recipeName)
				.orElseThrow(()->new RuntimeException("Recipe not available"));
		
		
		try {
			Set<IngredientDTO> DTOs = new HashSet<>();
			Set<ingredient> ingredients = r.getIngredients();
			if(ingredients.isEmpty())
				throw new RuntimeException("No ingredients availabel");
			for(ingredient i: ingredients){
				IngredientDTO dto = IngredientDTO.builder()
						.ingredientName(i.getId().getIngredient())
						.grams(i.getGrams())
						.build();
				DTOs.add(dto);
			}
			return DTOs;
		}catch(RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Transactional
	@Override
	public Recipe addRecipe(POSTRecipeDTO pOSTRecipeDTO){
		if(recipeRepository.findByRecipeName(pOSTRecipeDTO.getRecipeName()).isPresent())
		{
			throw new RuntimeException("Recipe already exists");
		}
		
		category category = categoryRepository.findById(pOSTRecipeDTO.getCategoryId())
				.orElseThrow(()->new RuntimeException("Category Not Found"));
		
		chef chef = chefRepository.findById(pOSTRecipeDTO.getChefId())
				.orElseThrow(()->new RuntimeException("Chef Not Found"));
		
		Recipe recipe = Recipe.builder()
				.recipeName(pOSTRecipeDTO.getRecipeName())
				.description(pOSTRecipeDTO.getDescription())
				.preparationTime(pOSTRecipeDTO.getPreparationTime())
				.cookingTime(pOSTRecipeDTO.getCookingTime())
				.difficultyLevel(pOSTRecipeDTO.getDifficultyLevel())
				.rating(pOSTRecipeDTO.getRating())
				.imageUrl(pOSTRecipeDTO.getImageUrl())
				.plateImageUrl(pOSTRecipeDTO.getPlateImageUrl())
				.category(category)
				.chef(chef)
				.reviews(new HashSet<>())
				.build();
		
		Recipe savedRecipe = recipeRepository.save(recipe);
		
		
		NutritionInformation ni = NutritionInformation.builder()
				.recipe(savedRecipe)
				.calories(pOSTRecipeDTO.getNi().getCalories())
				.total_fat(pOSTRecipeDTO.getNi().getTotal_fat())
				.cholesterol(pOSTRecipeDTO.getNi().getCholesterol())
				.carbohydrates(pOSTRecipeDTO.getNi().getCarbohydrates())
				.protein(pOSTRecipeDTO.getNi().getProtein())
				.sugar(pOSTRecipeDTO.getNi().getSugar())
				.sodium(pOSTRecipeDTO.getNi().getSodium())
				.fiber(pOSTRecipeDTO.getNi().getFiber())
				.zinc(pOSTRecipeDTO.getNi().getZinc())
				.magnesium(pOSTRecipeDTO.getNi().getMagnesium())
				.potassium(pOSTRecipeDTO.getNi().getPotassium())
				.build();
		
		nutritionInformationRepository.save(ni);
		
		Set<ingredient> ingredients = new HashSet<>();
		
		for(IngredientDTO iDTO: pOSTRecipeDTO.getIngredients()){
			IngredientId id = IngredientId.builder()
					.recipeId(recipe.getRecipeId())
					.ingredient(iDTO.getIngredientName())
					.build();
			
			ingredient i = ingredient.builder()
					.id(id)
					.grams(iDTO.getGrams())
					.recipe(savedRecipe)
					.build();
			ingredients.add(i);
		}
		ingredientsRepository.saveAll(ingredients);
		
		List<Instruction> instructions = new ArrayList<>();
		for(InstructionDTO iDTO: pOSTRecipeDTO.getInstructions()) {
			InstructionId id = InstructionId.builder()
					.recipeId(recipe.getRecipeId())
					.instructionOrder(iDTO.getOrder())
					.build();
			
			Instruction i = Instruction.builder()
					.id(id)
					.instruction(iDTO.getInstruction())
					.recipe(savedRecipe)
					.build();
			
			instructions.add(i);
		}
		instructionRepository.saveAll(instructions);
		savedRecipe.setIngredients(ingredients);
		savedRecipe.setNutritionInformation(ni);
		savedRecipe.setInstructions(instructions);
		
		final String chefName = chef.getFirstName()+" "+chef.getLastName();
		final Set<user> followers = chef.getFollowers();
		String messageTitle = "New recipe uploaded";
		String messageBody = chefName+ " uploaded a new recipe. Check it out now";
		for(user follower: followers){
			Notification notification = new Notification();
			notification.setUser(follower);
			notification.setTitle(messageTitle);
			notification.setMessage(messageBody);
			notificationRepository.save(notification);
			Optional<UserToken> userTokenOptional = userTokenRepository.findByUserId(follower.getId());
			if(userTokenOptional.isPresent()) {
				UserToken userToken = userTokenOptional.get();
				try {
					firebaseService.sendNotification(userToken.getToken(),notification.getTitle(),notification.getMessage());	
				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Failed to send push notification");
				}
			}
		}
		
		Notification notification = new Notification();
		notification.setUser(chef);	
		notification.setTitle("Recipe uploaded successfully");
		notification.setMessage("Your recipe "+pOSTRecipeDTO.getRecipeName()+" has been uploaded successfully");
		notificationRepository.save(notification);
		Optional<UserToken> chefTokenOptional = userTokenRepository.findByUserId(chef.getId());
		if(chefTokenOptional.isPresent()) {
			UserToken chefToken = chefTokenOptional.get();
			try {
				firebaseService.sendNotification(chefToken.getToken(), notification.getTitle(), notification.getMessage());
			}catch(Exception e) {
				throw new RuntimeException("Failed to send push notification "+e.getMessage());						
			}
		}
		return recipeRepository.save(savedRecipe);
	}

	@Override
	public boolean deleteRecipeById(UUID recipeId) {
		if(recipeRepository.existsById(recipeId)) {
			recipeRepository.deleteById(recipeId);
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean deleteRecipeByName(String recipeName) {
		Optional<Recipe> recipe = recipeRepository.findByRecipeName(recipeName);
		if(recipe.isPresent()) {
			recipeRepository.deleteByRecipeName(recipeName);
			return true;
		}
		return false;
	}

}