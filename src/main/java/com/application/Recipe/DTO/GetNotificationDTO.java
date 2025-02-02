package com.application.Recipe.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetNotificationDTO{
//	private UserDTO userDTO;
	private String notificationId;
	private String title;
	private String message;
	private boolean isRead;
	private LocalDateTime createdAt;
}
