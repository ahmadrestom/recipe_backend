package com.application.Recipe.Services;

import java.util.Set;

import com.application.Recipe.DTO.GetReviewDTO;
import com.application.Recipe.Models.Review;

import jakarta.transaction.Transactional;

public interface ReviewService {
	
	public Set<GetReviewDTO> GetRecipeReviews(Integer recipeId);
	
	@Transactional
	public void deleteReview(Integer recipeId);
	
	@Transactional
	public Review addReview(Integer recipeId, String review);
	
	@Transactional
	public void likeReview(Integer recipeId, Integer userId);
	
	@Transactional
	public void dislikeReview(Integer recipeId, Integer userId);
	
	@Transactional
	public void unlikeReview(Integer recipeId, Integer userId);
	
	@Transactional
	public void undislikeReview(Integer recipeId, Integer userId);

}
