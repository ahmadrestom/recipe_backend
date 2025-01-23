package com.application.Recipe.DTO;

import java.util.UUID;

import com.application.Recipe.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FollowerDTO {
	private UUID id;
	private String firstName;
	private String lastName;
	private String imageUrl;
	private Role role;

}
