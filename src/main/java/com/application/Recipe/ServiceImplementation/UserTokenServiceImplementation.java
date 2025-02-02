package com.application.Recipe.ServiceImplementation;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.Recipe.Models.UserToken;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Repository.UserTokenRepository;
import com.application.Recipe.Services.UserTokenService;

@Service
public class UserTokenServiceImplementation implements UserTokenService{
	
	@Autowired
	private UserTokenRepository token_repository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void saveUserToken(UUID userId, String token){
		try {
			userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
			UserToken userToken = new UserToken(userId, token);
	        token_repository.save(userToken);
		}catch(Exception e){
			throw new RuntimeException(e);			
		}
    }
	
	@Override
	public String getTokenByUserId(UUID userId){
        Optional<UserToken> userToken = token_repository.findByUserId(userId);
        return userToken.map(UserToken::getToken).orElse(null);
    }
	
	@Override
	public void deleteUserToken(String token){
		UserToken userToken = token_repository.findByToken(token);
		token_repository.delete(userToken);
    }
	
	

}
