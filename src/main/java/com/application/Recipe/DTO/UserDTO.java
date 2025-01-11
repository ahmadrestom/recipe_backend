package com.application.Recipe.DTO;

import java.util.UUID;

import com.application.Recipe.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private String imageUrl;
	private Role role;

}
