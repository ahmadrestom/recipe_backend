package com.application.Recipe.Controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.DTO.GetNotificationDTO;
import com.application.Recipe.DTO.NotificationDTO;
import com.application.Recipe.Models.Notification;
import com.application.Recipe.Services.NotificationService;

@RestController
@RequestMapping("/api/v2/notification")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/getAllNotifications")
	public ResponseEntity<?> getAllNotifications(){
		List<GetNotificationDTO> notifications = notificationService.getNotificationsByUserId();
		if(notifications.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			return ResponseEntity.ok(notifications);
		}
	}
	
	@PostMapping("/createNotification")
	public ResponseEntity<?> createNotification(@RequestBody NotificationDTO dto){
		Notification n = notificationService.createNotification(
					dto.getTitle(), dto.getMessage()
				);
		if(n!=null) {
			return ResponseEntity.ok(n);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping("/markAsRead/{notificationId}")
	public ResponseEntity<?> markAsRead(@PathVariable UUID notificationId){
		notificationService.markAsRead(notificationId);
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
		return ResponseEntity.ok("Marked as read");
	}
	
	@DeleteMapping("/deleteNotification/{notificationId}")
	public ResponseEntity<?> deleteNotification(@PathVariable UUID notificationId){
		notificationService.deleteNotificationById(notificationId);
		return ResponseEntity.ok("Delete successfully");
	}
}