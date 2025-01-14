package com.application.Recipe.ServiceImplementation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.Recipe.DTO.ChefDTO;
import com.application.Recipe.DTO.UserFavoritesDTO;
import com.application.Recipe.DTO.chefDTO_forRecipeGET;
import com.application.Recipe.Enums.Role;
import com.application.Recipe.Models.Recipe;
import com.application.Recipe.Models.chef;
import com.application.Recipe.Models.user;
import com.application.Recipe.Repository.ChefRepository;
import com.application.Recipe.Repository.RecipeRepository;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.application.Recipe.Repository.recentSearchRepository;
@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository user_repository;
	
	@Autowired
	ChefRepository chefRepository;
	
	@Autowired
	recentSearchRepository recentSearchRepository;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Override
	public chefDTO_forRecipeGET getChefData(UUID id){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();
		user u = user_repository.findByEmail(email).orElse(null);
		if(u!=null) {
			chef c = chefRepository.findById(u.getId()).orElse(null);
			if(c!=null) {
				chefDTO_forRecipeGET chefDTO = chefDTO_forRecipeGET.builder()
						.chefId(c.getId())
						.bio(c.getBio())
						.firstName(c.getFirstName())
						.lastName(c.getLastName())
						.image_url(c.getImage_url())
						.location(c.getLocation())
						.phone_number(c.getPhone_number())
						.years_experience(c.getYears_experience())
						.build();
				return chefDTO;
			}
			return null;
		}
		return null;
	}
	

	@Override
	public String createUser(user user){
		user_repository.save(user);
		return "User Created Successfully";
	}

	@Override
	public boolean updateUser(user user) {
		if(user.getId() == null || !user_repository.existsById(user.getId())) {
			return false;
		}
		user existingUser = user_repository.findById(user.getId()).orElse(null);
		if(existingUser == null) {
			return false;
		}
		if (user.getFirstName() != null) {
	        existingUser.setFirstName(user.getFirstName());
	    }
	    if (user.getLastName() != null) {
	        existingUser.setLastName(user.getLastName());
	    }
	    if (user.getEmail() != null) {
	        existingUser.setEmail(user.getEmail());
	    }
	    if (user.getPassword() != null) {
	        existingUser.setPassword(user.getPassword());
	    }
	    if (user.getImage_url() != null) {
	        existingUser.setImage_url(user.getImage_url());
	    }
	    if (user.getRole() != null) {
	        existingUser.setRole(user.getRole());
	    }
        
        user_repository.save(existingUser);
        return true;
	}
	
	@Override
	@Transactional
	public boolean deleteUserByEmail(String email) {
		Optional<user> user = user_repository.findByEmail(email);
		if(user.isPresent()) {
			user_repository.deleteByEmail(email);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Optional<user> getUserById(UUID user_id){
		return user_repository.findById(user_id);
	}
	
	@Override
	public Optional<user> getUserByEmail(String user_email) {
		return user_repository.findByEmail(user_email);
	}

	@Override
	public List<user> getAllUsers() {
		List<user> AllUsers= user_repository.findAll();
		return AllUsers;
	}

	@Override
    public boolean upgradeToChef(ChefDTO chefDTO){
        Optional<user> userOptional = user_repository.findById(chefDTO.getUserId());
        if (userOptional.isPresent()) {
            user existingUser = userOptional.get();
            // Check if the user is not already a chef
            if (!(existingUser instanceof chef)) {
                // Update user role to "chef" and insert chef information
            	existingUser.setRole(Role.CHEF);
            	user_repository.upgradeUserToChef(existingUser.getId(), chefDTO.getLocation(), chefDTO.getPhone_number(),
                        chefDTO.getBio(), chefDTO.getYears_experience());
                return true;
            } else {
                // User is already a chef, no need to upgrade
                return false;
            }
        }
        return false;
    }

	@Transactional
	@Override
	public void followChef(UUID chefId) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserEmail = userDetails.getUsername();
		Optional<user> userOptional = user_repository.findByEmail(currentUserEmail);
		if(userOptional.isEmpty())
			throw new EntityNotFoundException("User not found");
		
		user user = userOptional.get();
		if(user instanceof chef){
			throw new IllegalStateException("A chef cannot follow anther chef.");
		}
		chef chef = chefRepository.findById(chefId)
				.orElseThrow(()->new RuntimeException("Chef not found."));
		
		user.getFollowingChefs().add(chef);
		
		chef.getFollowers().add(user);
		chefRepository.save(chef);
		user_repository.save(user);
		
	}
	
	@Transactional
	@Override
	public void unfollowChef(UUID chefId){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserEmail = userDetails.getUsername();
		Optional<user> userOptional = user_repository.findByEmail(currentUserEmail);
		if(userOptional.isEmpty())
			throw new EntityNotFoundException("User not found");
		chef chef = chefRepository.findById(chefId)
				.orElseThrow(()->new RuntimeException("Chef not found."));
		user user = userOptional.get();
		user.getFollowingChefs().remove(chef);
		chef.getFollowers().remove(user);
		chefRepository.save(chef);
		user_repository.save(user);
		
	}
	
	@Override
	public boolean addFavoriteRecipe(UUID recipeId){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currentUserEmail = userDetails.getUsername();
			Optional<user> userOptional = user_repository.findByEmail(currentUserEmail);
			Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
			if(userOptional.isPresent() && recipeOptional.isPresent())
			{
				if(userOptional.get().getFavorites().contains(recipeOptional.get().getRecipeId()))
					throw new RuntimeException("Recipe is already in favorites");
				userOptional.get().getFavorites().add(recipeOptional.get());
				user_repository.save(userOptional.get());
				return true;
			}
			else if(userOptional.isEmpty())
			{
				throw new EntityNotFoundException("User not found");			
			}
			else
			{
				throw new EntityNotFoundException("Recipe not found");
			}
		}catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean removeFavoriteRecipe(UUID recipeId) {
	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String currentUserEmail = userDetails.getUsername();
	        Optional<user> userOptional = user_repository.findByEmail(currentUserEmail);
	        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
	        if (userOptional.isPresent() && recipeOptional.isPresent()) {
	            user currentUser = userOptional.get();
	            Recipe recipeToRemove = recipeOptional.get();
	            if (currentUser.getFavorites().contains(recipeToRemove)) {
	                currentUser.getFavorites().remove(recipeToRemove);
	                user_repository.save(currentUser); 
	                return true;
	            } else {
	                throw new RuntimeException("Recipe not found in favorites");
	            }
	        } else if (userOptional.isEmpty()) {
	            throw new EntityNotFoundException("User not found");
	        } else {
	            throw new EntityNotFoundException("Recipe not found");
	        }
	    } catch (Exception e) {
	        return false;
	    }
	}


	@Override
	public Set<UserFavoritesDTO> getFavoriteRecipes() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserEmail = userDetails.getUsername();
		Optional<user> userOptional = user_repository.findByEmail(currentUserEmail);
		if(userOptional.isEmpty())
			throw new EntityNotFoundException("User not found");
		
		user user = userOptional.get();
		Set<Recipe> recipes = user.getFavorites();
		
		if(recipes.isEmpty())
			throw new RuntimeException("You have no favorite recipes. Add some now!");
		
		Set<UserFavoritesDTO> recipeDTOs = new HashSet<>();
		
		for(Recipe recipe:recipes) {
			UserFavoritesDTO dto = UserFavoritesDTO.builder()
					.recipeId(recipe.getRecipeId())
					.recipeName(recipe.getRecipeName())
					.description(recipe.getDescription())
					.timeUploaded(recipe.getTimeUploaded())
					.preparationTime(recipe.getPreparationTime())
					.cookingTime(recipe.getCookingTime())
					.difficultyLevel(recipe.getDifficultyLevel())
					.rating(recipe.getRating())
					.imageUrl(recipe.getImageUrl())
					.categoryName(recipe.getCategory().getCategory_name())
					.chefName(recipe.getChef().getFirstName()+" "+recipe.getChef().getLastName())
					.chefPictureUrl(recipe.getChef().getImage_url())
					.build();
			recipeDTOs.add(dto);
		}
		return recipeDTOs;
	}
}
