package com.application.Recipe.Services;

import java.util.Set;
import java.util.UUID;

import com.application.Recipe.DTO.GetReviewDTO;
import com.application.Recipe.Models.Review;

import jakarta.transaction.Transactional;

public interface ReviewService {
	
	public Set<GetReviewDTO> GetRecipeReviews(UUID recipeId);
	
	@Transactional
	public void deleteReview(UUID recipeId);
	
	@Transactional
	public Review addReview(UUID recipeId, String review);
	
	/*@Transactional
	public void likeReview(UUID recipeId, UUID userId);
	
	@Transactional
	public void dislikeReview(UUID recipeId, UUID userId);
	
	@Transactional
	public void unlikeReview(UUID recipeId, UUID userId);
	
	@Transactional
	public void undislikeReview(UUID recipeId, UUID userId);
    */
}
