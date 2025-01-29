package com.application.Recipe.ServiceImplementation;

import java.util.List;
import java.util.UUID;

import com.application.Recipe.Models.Notification;
import com.application.Recipe.Services.NotificationService;

public class NotificationServiceImplementation implements NotificationService{

	@Override
	public List<Notification> getNotificationsByUserId(UUID userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notification createNotification(UUID userId, String title, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markAsRead(UUID notificationId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNotificationById(UUID notificationId) {
		// TODO Auto-generated method stub
		
	}

}
