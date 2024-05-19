package com.application.Recipe.Services;

import java.util.List;
import java.util.Optional;

import com.application.Recipe.Models.user;

public interface UserService {
	public String createUser(user user);
	public boolean updateUser(user user);
	public boolean deleteUserByEmail(String email);
	public Optional<user> getUserById(Integer user_id);
	public List<user> getAllUsers();
	Optional<user> getUserByEmail(String user_email);
}
