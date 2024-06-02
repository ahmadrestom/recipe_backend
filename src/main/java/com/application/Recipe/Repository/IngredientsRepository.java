package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Recipe.Models.ingredient;

public interface IngredientsRepository extends JpaRepository<ingredient, Integer>{

}
