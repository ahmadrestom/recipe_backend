package com.application.Recipe.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class chefDTO_forRecipeGET {
	
	private Integer chefId;
	
	private String firstName;
	
	private String lastName;
	
	private String image_url;
	
	private String location;
	
	private String phone_number;
	
	private String bio;
	
	private Integer years_experience;

}
