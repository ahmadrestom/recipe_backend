package com.application.Recipe.DTO;

import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GETRecipeDTOProfile{
	
	private UUID recipeId;
	private String recipeName;
	private Integer preparationTime;
	private double rating;
	private String imageUrl;

}
