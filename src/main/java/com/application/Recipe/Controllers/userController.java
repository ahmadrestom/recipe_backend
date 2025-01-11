package com.application.Recipe.Controllers;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.DTO.ChefDTO;
import com.application.Recipe.DTO.UserDTO;
import com.application.Recipe.DTO.UserFavoritesDTO;
import com.application.Recipe.Models.recentSearch;
import com.application.Recipe.Models.user;
import com.application.Recipe.Services.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v2/user")
public class userController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getUser")
	public ResponseEntity<UserDTO> getCurrentUser(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();
		
		user user = userService.getUserByEmail(email).orElse(null);
		if(user != null) {
			UserDTO userDTO = UserDTO.builder()
					.id(user.getId())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.email(user.getEmail())
					.imageUrl(user.getImage_url())
					.role(user.getRole())
					.build();
			return ResponseEntity.ok(userDTO);
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/deleteUser/{email}")
	public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email){
		boolean isDeleted = userService.deleteUserByEmail(email);
		if(isDeleted) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<user> getUserById(@PathVariable UUID id){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = userDetails.getUsername();
		Optional<user> optionalUser = userService.getUserById(id);	
		if (optionalUser.isPresent()) {
	        user requestedUser = optionalUser.get();
	        if (requestedUser.getEmail().equals(userEmail)) {
	            return ResponseEntity.ok(requestedUser);
	        } else{
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/upgradeToChef")
	public ResponseEntity<String> upgradeToChef(@RequestBody ChefDTO chefDTO){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserEmail = userDetails.getUsername();
		Optional<user> currentUserOptional = userService.getUserByEmail(currentUserEmail);
		if(currentUserOptional.isPresent()) {
			user currentUser = currentUserOptional.get();
			chefDTO.setUserId(currentUser.getId());
			boolean isUpgraded = userService.upgradeToChef(chefDTO);
			if (isUpgraded) {
                return ResponseEntity.ok("User upgraded to Chef successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody user updatedUser){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = userDetails.getUsername();
		Optional<user> existingUser = userService.getUserById(id);
		 if (existingUser.isPresent() && existingUser.get().getEmail().equals(userEmail)) {
			 updatedUser.setId(id);
			 boolean isUpdated = userService.updateUser(updatedUser);
			 if (isUpdated) {
		            return ResponseEntity.ok("User updated successfully");
		        } else {
		            return ResponseEntity.notFound().build();
		        }
		 }else {
			 return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		 }
	}
	
	@GetMapping("/getRecentSearches")
	public ResponseEntity<Set<recentSearch>> getUserRecentSearches(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = userDetails.getUsername();
		Optional<user> userOptional = userService.getUserByEmail(userEmail);
		if(userOptional.isPresent()) {
			user user = userOptional.get();
			Set<recentSearch> recentSearches = user.getRecentSearches();
			
			return ResponseEntity.ok(recentSearches);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/followChef/{chefId}")
	public ResponseEntity<?> followChef(@PathVariable UUID chefId)
	{
		try {
			userService.followChef(chefId);
			return ResponseEntity.ok("Chef followed");
		}catch(EntityNotFoundException e){
			return ResponseEntity.notFound().build();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/unfollowChef/{chefId}")
	public ResponseEntity<?> unfollowChef(@PathVariable UUID chefId)
	{
		try {
			userService.unfollowChef(chefId);
			return ResponseEntity.ok("Chef unfollowed");
		}catch(EntityNotFoundException e){
			return ResponseEntity.notFound().build();
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@PostMapping("/addFavoriteRecipe/{recipeId}")
	public ResponseEntity<?> addFavoriteRecipe(@PathVariable UUID recipeId)
	{
		try {
			userService.addFavoriteRecipe(recipeId);
			return ResponseEntity.ok("Recipe added to favorites");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);	
		}
	}
	
	@DeleteMapping("/removeFavoriteRecipe/{recipeId}")
	public ResponseEntity<?> removeFavoriteRecipe(@PathVariable UUID recipeId)
	{
		try {
			userService.removeFavoriteRecipe(recipeId);
			return ResponseEntity.ok("Recipe removed from favorites");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);	
		}
	}
	
	@GetMapping("/getUserFavorites")
	public ResponseEntity<?> getUserFavorites()
	{
		try {
			Set<UserFavoritesDTO> recipes = userService.getFavoriteRecipes();
			return ResponseEntity.ok(recipes);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();	
		}
	}
	
}