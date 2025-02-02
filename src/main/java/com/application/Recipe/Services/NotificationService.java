package com.application.Recipe.Services;

import java.util.List;
import java.util.UUID;

import com.application.Recipe.DTO.GetNotificationDTO;
import com.application.Recipe.Models.Notification;

public interface NotificationService {
	
	public List<GetNotificationDTO> getNotificationsByUserId();
	public Notification createNotification(String title, String message);
	public void markAsRead(UUID notificationId);
	public void deleteNotificationById(UUID notificationId);

}
