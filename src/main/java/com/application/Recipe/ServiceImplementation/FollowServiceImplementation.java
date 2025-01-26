package com.application.Recipe.ServiceImplementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.Repository.FollowRepository;
import com.application.Recipe.Services.FollowService;

@Service
public class FollowServiceImplementation implements FollowService{
	
	@Autowired
	private FollowRepository followRepository;
	
	@Override
	public boolean isUserFollowingChef(UUID userId, UUID chefId) {
        return followRepository.existsByUser_IdAndChef_Id(userId, chefId);
    }
	
	
	
	

}
