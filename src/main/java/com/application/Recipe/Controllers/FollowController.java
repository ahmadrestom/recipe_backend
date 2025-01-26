package com.application.Recipe.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.Services.FollowService;

@RestController
@RequestMapping("/api/v2/follow")
public class FollowController {
	
	@Autowired
	private FollowService followService;
	
	@GetMapping("/isFollowing/{userId}/{chefId}")
    public boolean isUserFollowingChef(@PathVariable UUID userId, @PathVariable UUID chefId) {
        return followService.isUserFollowingChef(userId, chefId);
    }
	

}
