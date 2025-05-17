package com.application.Recipe.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.Recipe.Models.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID>{
	Optional<Recipe> findByRecipeName(String recipeName);
	Long deleteByRecipeName(String recipeName);
	@Query("SELECT r FROM Recipe r WHERE r.timeUploaded >= :cutoffTime")
    List<Recipe> findRecentRecipes(@Param("cutoffTime") LocalDateTime cutoffTime);
	List<Recipe> findAllByChefId(UUID chefId);
	Page<Recipe> findAllByOrderByTimeUploadedDesc(Pageable pageable);
	List<Recipe> findTop10ByOrderByTimeUploadedDesc();
	List<Recipe> findByCategory_CategoryName(String categoryName);
	Page<Recipe> findAll(Pageable pageable);


}
