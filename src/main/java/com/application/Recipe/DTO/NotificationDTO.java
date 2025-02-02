package com.application.Recipe.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationDTO {
	private UUID userId;
    private String title;
    private String message;
}
