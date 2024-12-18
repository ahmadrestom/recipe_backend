package com.application.Recipe.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChefDTO {
	private UUID userId;
	private String location;
	private String bio;
	private Integer years_experience;
	private String phone_number;

}
