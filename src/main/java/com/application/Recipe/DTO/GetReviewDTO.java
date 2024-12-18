package com.application.Recipe.DTO;

import java.time.LocalDateTime;
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
public class GetReviewDTO {
	
	private String text;
	private Integer likes;
	private Integer dislikes;
	private LocalDateTime timeUploaded;
	private UUID userId;

}
