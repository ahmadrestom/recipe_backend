package com.application.Recipe.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.Recipe.Models.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>{
	Optional<Recipe> findByRecipeName(String recipeName);
	Long deleteByRecipeName(String recipeName);
	@Query("SELECT r FROM Recipe r WHERE r.timeUploaded >= :cutoffTime")
    List<Recipe> findRecentRecipes(@Param("cutoffTime") LocalDateTime cutoffTime);

}
