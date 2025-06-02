package com.application.Recipe.ServiceImplementation;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.application.Recipe.FirebaseService;
import com.application.Recipe.CompositeKeys.ReviewId;
import com.application.Recipe.DTO.GetReviewDTO;
import com.application.Recipe.DTO.ReviewUserData;
import com.application.Recipe.Models.Notification;
import com.application.Recipe.Models.Recipe;
import com.application.Recipe.Models.Review;
import com.application.Recipe.Models.UserToken;
import com.application.Recipe.Models.user;
import com.application.Recipe.Repository.NotificationRepository;
import com.application.Recipe.Repository.RecipeRepository;
import com.application.Recipe.Repository.ReviewRepository;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Repository.UserTokenRepository;
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
	
	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
    private FirebaseService firebaseService;
	
	@Autowired
	UserTokenRepository userTokenRepository;
	
	@Override
	public Set<GetReviewDTO> GetRecipeReviews(UUID recipeId) {
	    Recipe r = recipeRepository.findById(recipeId)
	            .orElseThrow(() -> new EntityNotFoundException("Recipe not available"));

	    Set<Review> reviews = r.getReviews();
	    if (reviews.isEmpty())
	        throw new EmptyResultDataAccessException("No reviews for this recipe", 1);
	    Set<GetReviewDTO> dtos = new TreeSet<>(Comparator.comparing(GetReviewDTO::getTimeUploaded).reversed());

	    for (Review review : reviews) {
	        ReviewUserData u = ReviewUserData.builder()
	                .firstName(review.getUser().getFirstName())
	                .lastName(review.getUser().getLastName())
	                .imageUrl(review.getUser().getImage_url())
	                .userId(review.getUser().getId())
	                .build();

	        GetReviewDTO dto = GetReviewDTO.builder()
	                .text(review.getText().replaceAll("\r\n", " ").trim())
	                .timeUploaded(review.getTimeUploaded())
	                .user(u)
	                .build();

	        dtos.add(dto);
	    }

	    return dtos;
	}


	@Transactional
	@Override
	public void deleteReview(UUID recipeId){
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
	public GetReviewDTO addReview(UUID recipeId, String review){
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
	    
	    ReviewUserData u = ReviewUserData.builder()
                .firstName(createdReview.getUser().getFirstName())
                .lastName(createdReview.getUser().getLastName())
                .imageUrl(createdReview.getUser().getImage_url())
                .userId(createdReview.getUser().getId())
                .build();
	    
	    reviewRepository.save(createdReview);
	    
	    GetReviewDTO reviewDto = GetReviewDTO.builder()
	    		.text(createdReview.getText())
	    		.user(u)
	    		.timeUploaded(createdReview.getTimeUploaded())
	    		.build();
	    
	    Notification notification = new Notification();
	    notification.setUser(r.getChef());
	    notification.setTitle("New review on "+r.getRecipeName()+ " recipe");
	    notification.setMessage(createdReview.getUser().getFirstName()+
	    		" "+
	    		createdReview.getUser().getLastName()+
	    		" added a review on "+
	    		r.getRecipeName()+
	    		". Check it out!"
	    		);
	    notificationRepository.save(notification);
	    
	    Optional<UserToken> chefTokenOptional = userTokenRepository.findByUserId(r.getChef().getId());
		if(chefTokenOptional.isPresent()) {
			UserToken chefToken = chefTokenOptional.get();
			try {
				firebaseService.sendNotification(chefToken.getToken(), notification.getTitle(), notification.getMessage());
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to send push notification "+e.getMessage());
			}
		}
	    
	    return reviewDto; 
	}

	/*@Transactional
	@Override
	public void likeReview(UUID recipeId, UUID userId){
		
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
	public void dislikeReview(UUID recipeId, UUID userId){
		
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
	public void unlikeReview(UUID recipeId, UUID userId) {
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
	public void undislikeReview(UUID recipeId, UUID userId) {
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
		
	}*/
	
	

}
