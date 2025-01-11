package com.application.Recipe.DTO;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReviewUserData {
	private String imageUrl;
	private String firstName;
	private String lastName;
	private UUID userId;

}
