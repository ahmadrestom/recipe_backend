package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Recipe.CompositeKeys.ReviewId;
import com.application.Recipe.Models.Review;

public interface ReviewRepository extends JpaRepository<Review,ReviewId>{

}
