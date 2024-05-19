package com.application.Recipe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChefDTO {
	private Integer userId;
	private String location;
	private String bio;
	private Integer years_of_experience;
	private String phone_number;

}
