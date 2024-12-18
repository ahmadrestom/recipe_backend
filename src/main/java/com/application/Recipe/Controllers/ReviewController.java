package com.application.Recipe.Controllers;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.DTO.GetReviewDTO;
import com.application.Recipe.Services.ReviewService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v2/review")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@PutMapping("/likeReview/{recipeId}/{userId}")
	public ResponseEntity<?> likeReview(@PathVariable UUID recipeId, @PathVariable UUID userId)
	{
		try {
			reviewService.likeReview(recipeId, userId);
			return ResponseEntity.ok("Review liked");
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/dislikeReview/{recipeId}/{userId}")
	public ResponseEntity<?> dislikeReview(@PathVariable UUID recipeId, @PathVariable UUID userId)
	{
		try {
			reviewService.dislikeReview(recipeId, userId);
			return ResponseEntity.ok("Review unliked");
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/unlikeReview/{recipeId}/{userId}")
	public ResponseEntity<?> unlikeReview(@PathVariable UUID recipeId, @PathVariable UUID userId)
	{
		try {
			reviewService.unlikeReview(recipeId, userId);
			return ResponseEntity.ok("Review unliked");
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	@PutMapping("/undislikeReview/{recipeId}/{userId}")
	public ResponseEntity<?> undislikeReview(@PathVariable UUID recipeId, @PathVariable UUID userId)
	{
		try {
			reviewService.undislikeReview(recipeId, userId);
			return ResponseEntity.ok("Review undisliked");
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/getRecipeReviews/{recipeId}")
	public ResponseEntity<?> getRecipeReviews(@PathVariable UUID recipeId)
	{
		try {
			Set<GetReviewDTO> dtos = reviewService.GetRecipeReviews(recipeId);
			return ResponseEntity.ok(dtos);
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteReview/{recipeId}")
	public ResponseEntity<?> deleteReview(@PathVariable UUID recipeId)
	{
		try {
			reviewService.deleteReview(recipeId);
			return ResponseEntity.ok("Review deleted successfully");
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/addReview/{recipeId}")
	public ResponseEntity<?> createReview(@PathVariable UUID recipeId, @RequestBody String reviewText)
	{
		try {
			reviewService.addReview(recipeId, reviewText);
			return ResponseEntity.ok("Review created successfully");
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	

}
