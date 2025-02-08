package com.application.Recipe.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.application.Recipe.DTO.ChefDTO;
import com.application.Recipe.DTO.FollowerDTO;
import com.application.Recipe.DTO.FollowerStatsDTO;
import com.application.Recipe.DTO.UserFavoritesDTO;
import com.application.Recipe.DTO.chefDTO_forRecipeGET;
import com.application.Recipe.Models.chef;
import com.application.Recipe.Models.user;

import jakarta.transaction.Transactional;

public interface UserService {
	public String createUser(user user);
	public boolean updateUser(user user);
	public boolean deleteUserByEmail(String email);
	public Optional<user> getUserById(UUID user_id);
	public void UpdateUserPicture(UUID userId, String imageUrl);
	
	@Transactional
	public void DeleteUserPicture(UUID userId);
	
	public List<user> getAllUsers();
	Optional<user> getUserByEmail(String user_email);
	public boolean upgradeToChef(ChefDTO chefDTO);
	public boolean addFavoriteRecipe(UUID recipeId);
	public boolean removeFavoriteRecipe(UUID recipeId);
	public Set<UserFavoritesDTO> getFavoriteRecipes();
	
	@Transactional
	public void followChef(UUID chefId);
	
	@Transactional
	public void unfollowChef(UUID chefId);
	
	@Transactional
	public Set<FollowerDTO> getAllFollowers(UUID chefId);
	
	@Transactional
	public Set<FollowerDTO> getAllFollowings(UUID userId);
	
	public chefDTO_forRecipeGET getChefData(UUID id);
	
	public FollowerStatsDTO getFollowerStats(UUID chefId);
}
