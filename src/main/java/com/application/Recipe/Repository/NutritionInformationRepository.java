package com.application.Recipe.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Recipe.Models.NutritionInformation;
import com.application.Recipe.Models.Recipe;

public interface NutritionInformationRepository extends JpaRepository<NutritionInformation, UUID>{

}
