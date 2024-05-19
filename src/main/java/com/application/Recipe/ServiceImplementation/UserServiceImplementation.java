package com.application.Recipe.ServiceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.Models.user;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository user_repository;
	
	/*public UserServiceImplementation(UserRepository user_repository) {
		super();
		this.user_repository = user_repository;
	}*/

	@Override
	public String createUser(user user){
		user_repository.save(user);
		return "User Created Successfully";
	}

	@Override
	public boolean updateUser(user user) {
		if(user.getId() == null || !user_repository.existsById(user.getId())) {
			return false;
		}
		user existingUser = user_repository.findById(user.getId()).orElse(null);
		if(existingUser == null) {
			return false;
		}
		if (user.getFirstName() != null) {
	        existingUser.setFirstName(user.getFirstName());
	    }
	    if (user.getLastName() != null) {
	        existingUser.setLastName(user.getLastName());
	    }
	    if (user.getEmail() != null) {
	        existingUser.setEmail(user.getEmail());
	    }
	    if (user.getPassword() != null) {
	        existingUser.setPassword(user.getPassword());
	    }
	    if (user.getImage_url() != null) {
	        existingUser.setImage_url(user.getImage_url());
	    }
	    if (user.getRole() != null) {
	        existingUser.setRole(user.getRole());
	    }
        
        user_repository.save(existingUser);
        return true;
	}
	
	@Override
	@Transactional
	public boolean deleteUserByEmail(String email) {
		Optional<user> user = user_repository.findByEmail(email);
		if(user.isPresent()) {
			user_repository.deleteByEmail(email);
			System.out.print(user.isPresent());
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Optional<user> getUserById(Integer user_id){
		return user_repository.findById(user_id);
	}
	
	@Override
	public Optional<user> getUserByEmail(String user_email) {
		return user_repository.findByEmail(user_email);
	}

	@Override
	public List<user> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
