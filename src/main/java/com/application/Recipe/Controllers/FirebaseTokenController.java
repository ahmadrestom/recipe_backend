package com.application.Recipe.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.application.Recipe.Services.UserTokenService;

@RestController
@RequestMapping("/api/v2/deviceToken")
public class FirebaseTokenController {
	
	@Autowired
	private UserTokenService userTokenService;
	
	@PostMapping("/save/{userId}/{token}")
    public ResponseEntity<?> saveUserToken(@PathVariable UUID userId, @PathVariable String token) {
        try {
        	userTokenService.saveUserToken(userId, token);
        	return ResponseEntity.ok("Saved successfully");
        }catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	@GetMapping("/getToken/{userId}")
    public ResponseEntity<?> getTokenByUserId(@PathVariable UUID userId) {
        try {
        	String token= userTokenService.getTokenByUserId(userId);
        	return ResponseEntity.ok(token);        	
        }catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@DeleteMapping("/deleteToken/{token}")
    public void deleteUserToken(@PathVariable String token){
        userTokenService.deleteUserToken(token);
    }

}
