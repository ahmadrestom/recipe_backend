package com.application.Recipe.authentication;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.application.Recipe.Config.JwtService;
import com.application.Recipe.Enums.Role;
import com.application.Recipe.Models.UserToken;
import com.application.Recipe.Models.user;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Repository.UserTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	
	public AuthenticationResponse register(RegisterRequest request) {
		if (repository.existsByEmail(request.getEmail())) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
	    }
		var userr = user.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		repository.save(userr);
		
		var jwtToken = jwtService.generateToken(userr);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		var user = repository.findByEmail(request.getEmail())
				.orElseThrow();
		
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.firstName(user.getFirstName())
				.id(user.getId())
				.build();
	}	
	
}
