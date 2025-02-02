package com.application.Recipe.ServiceImplementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.application.Recipe.DTO.GetNotificationDTO;
import com.application.Recipe.DTO.UserDTO;
import com.application.Recipe.Models.Notification;
import com.application.Recipe.Models.user;
import com.application.Recipe.Repository.NotificationRepository;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Services.NotificationService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NotificationServiceImplementation implements NotificationService{

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public List<GetNotificationDTO> getNotificationsByUserId(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserEmail = userDetails.getUsername();
		Optional<user> userOptional = userRepository.findByEmail(currentUserEmail);
		if(userOptional.isEmpty())
			throw new EntityNotFoundException("User not found");
		
		user user = userOptional.get();
		UUID userId = user.getId();
//		UserDTO dto = UserDTO.builder()
//				.email(user.getEmail())
//				.firstName(user.getFirstName())
//				.lastName(user.getLastName())
//				.id(user.getId())
//				.role(user.getRole())
//				.imageUrl(user.getImage_url())
//				.build();
		List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);

		List<GetNotificationDTO> notificationDTOs = notifications.stream()
		        .map(notification -> new GetNotificationDTO(
		            notification.getId().toString(),
		            notification.getTitle(), 
		            notification.getMessage(),
		            notification.isRead(), 
		            notification.getCreatedAt() 
		        ))
		        .collect(Collectors.toList());

		    return notificationDTOs;
		
	}

	@Override
	public Notification createNotification(String title, String message) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserEmail = userDetails.getUsername();
		Optional<user> userOptional = userRepository.findByEmail(currentUserEmail);
		if(userOptional.isEmpty())
			throw new EntityNotFoundException("User not found");
		user user = userOptional.get();
		UUID userId = user.getId();
		Notification notification = new Notification(userId, title, message);
		notificationRepository.save(notification);
		return notification;
	}

	@Override
	public void markAsRead(UUID notificationId) {
		Notification notification = notificationRepository.findById(notificationId).orElseThrow(()->new RuntimeException("No notification found by this id"));
		notification.setRead(true);
		notificationRepository.save(notification);
	}

	@Override
    public void deleteNotificationById(UUID notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("No notification found with this ID");
        }
        notificationRepository.deleteById(notificationId);
    }

}
