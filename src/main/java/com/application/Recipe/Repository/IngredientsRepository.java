package com.application.Recipe.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Recipe.CompositeKeys.IngredientId;
import com.application.Recipe.Models.ingredient;

public interface IngredientsRepository extends JpaRepository<ingredient, IngredientId>{

}
