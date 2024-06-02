package com.application.Recipe.ServiceImplementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.application.Recipe.CompositeKeys.ReviewId;
import com.application.Recipe.DTO.GetReviewDTO;
import com.application.Recipe.Models.Recipe;
import com.application.Recipe.Models.Review;
import com.application.Recipe.Models.user;
import com.application.Recipe.Repository.RecipeRepository;
import com.application.Recipe.Repository.ReviewRepository;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Services.ReviewService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReviewServiceImplementation implements ReviewService{
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired 
	UserRepository userRepository;

	@Override
	public Set<GetReviewDTO> GetRecipeReviews(Integer recipeId) {
		Recipe r = recipeRepository.findById(recipeId)
				.orElseThrow(()-> new EntityNotFoundException("Recipe not available"));
		
		Set<Review> reviews = r.getReviews();
		if(reviews.isEmpty())
			throw new EmptyResultDataAccessException("No reviews for this recipe",1);
		
		Set<GetReviewDTO>  dtos = new HashSet<>();
		
		for(Review review: reviews) {
			GetReviewDTO dto = GetReviewDTO.builder()
					.text(review.getText())
					.likes(review.getLikes())
					.dislikes(review.getDislikes())
					.timeUploaded(review.getTimeUploaded())
					.build();
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Transactional
	@Override
	public void deleteReview(Integer recipeId){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserEmail = userDetails.getUsername();
        user user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        ReviewId id = ReviewId.builder()
        		.userId(user.getId())
        		.recipeId(recipeId)
        		.build();
        
        Review review = reviewRepository.findById(id)
        		.orElseThrow(()->new EntityNotFoundException("Review not found"));
        
        if(!review.getUser().equals(user))
        	throw new IllegalStateException("Only review creator can delete it");
        
        reviewRepository.deleteById(id);
	}

	@Transactional
	@Override
	public Review addReview(Integer recipeId, String review){
		Recipe r = recipeRepository.findById(recipeId)
				.orElseThrow(()-> new EntityNotFoundException("Recipe not available"));
		
		 UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String currentUserEmail = userDetails.getUsername();
	        user user = userRepository.findByEmail(currentUserEmail)
	                .orElseThrow(() -> new EntityNotFoundException("User not found"));
	        
	    ReviewId reviewId = ReviewId.builder()
	    		.recipeId(r.getRecipeId())
	    		.userId(user.getId())
	    		.build();
	    
	    Review createdReview = Review.builder()
	    		.id(reviewId)
	    		.text(review)
	    		.recipe(r)
	    		.user(user)
	    		.build();
	    
	    reviewRepository.save(createdReview);
	    
	    return createdReview; 
	}

	@Transactional
	@Override
	public void likeReview(Integer recipeId, Integer userId){
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails == null)
        	throw new SecurityException("User not authenticated");
		ReviewId reviewId = ReviewId.builder()
				.recipeId(recipeId)
				.userId(userId)
				.build();
		
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(()->new EntityNotFoundException("Review not found"));
		
		review.setLikes(review.getLikes() +1);
		reviewRepository.save(review);
	}

	@Transactional
	@Override
	public void dislikeReview(Integer recipeId, Integer userId){
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		if(userDetails == null)
	        	throw new SecurityException("User not authenticated");
			
		ReviewId reviewId = ReviewId.builder()
				.recipeId(recipeId)
				.userId(userId)
				.build();
		
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(()->new EntityNotFoundException("Review not found"));
		
		review.setDislikes(review.getDislikes()+1);
		
		reviewRepository.save(review);
	}

	@Override
	public void unlikeReview(Integer recipeId, Integer userId) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails == null)
        	throw new SecurityException("User not authenticated");
		ReviewId reviewId = ReviewId.builder()
				.recipeId(recipeId)
				.userId(userId)
				.build();
		
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(()->new EntityNotFoundException("Review not found"));
		if(review.getLikes()>0) {
			review.setLikes(review.getLikes() - 1);
			reviewRepository.save(review);
		}else {
			throw new RuntimeException("Should like to unlike");
		}
		
	}

	@Override
	public void undislikeReview(Integer recipeId, Integer userId) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails == null)
        	throw new SecurityException("User not authenticated");
		ReviewId reviewId = ReviewId.builder()
				.recipeId(recipeId)
				.userId(userId)
				.build();
		
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(()->new EntityNotFoundException("Review not found"));
		if(review.getDislikes()>0) {
			review.setLikes(review.getDislikes() - 1);
			reviewRepository.save(review);
		}else {
			throw new RuntimeException("Should dislike to undislike");
		}
		
	}
	
	

}
