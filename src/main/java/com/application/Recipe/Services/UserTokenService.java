package com.application.Recipe.Services;

import java.util.UUID;

import com.application.Recipe.Models.user;

public interface UserTokenService {
	
	public void saveUserToken(UUID userId, String token);
	public String getTokenByUserId(UUID userId);
	public void deleteUserToken(String token);
}
