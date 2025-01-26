package com.application.Recipe.Services;

import java.util.UUID;

public interface FollowService {
	
	public boolean isUserFollowingChef(UUID userId, UUID chefId);

}
