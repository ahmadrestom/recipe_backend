package com.application.Recipe.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.application.Recipe.DTO.ChefDTO;
import com.application.Recipe.DTO.UserFavoritesDTO;
import com.application.Recipe.Models.user;

import jakarta.transaction.Transactional;

public interface UserService {
	public String createUser(user user);
	public boolean updateUser(user user);
	public boolean deleteUserByEmail(String email);
	public Optional<user> getUserById(Integer user_id);
	public List<user> getAllUsers();
	Optional<user> getUserByEmail(String user_email);
	public boolean upgradeToChef(ChefDTO chefDTO);
	public boolean addFavoriteRecipe(Integer recipeId);
	public Set<UserFavoritesDTO> getFavoriteRecipes();
	
	@Transactional
	public void followChef(Integer chefId);
	
	@Transactional
	public void unfollowChef(Integer chefId);
}
