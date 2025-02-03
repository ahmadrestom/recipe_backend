package com.application.Recipe.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SpecialityLinkDTO {
	
	private UUID specialityId;
	private String speciality;
	private String description;

}
