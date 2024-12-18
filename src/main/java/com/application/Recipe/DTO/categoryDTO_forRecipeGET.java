package com.application.Recipe.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class categoryDTO_forRecipeGET {
	
	private UUID categoryId;
	
	private String categoryName;

}
