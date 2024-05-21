package com.application.Recipe.ServiceImplementation;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.Repository.recentSearchRepository;
import com.application.Recipe.CompositeKeys.RecentSearchId;
import com.application.Recipe.Models.recentSearch;
import com.application.Recipe.Models.user;
import com.application.Recipe.Repository.UserRepository;
import com.application.Recipe.Services.recentSearchService;

@Service
public class recentSearchServiceImplementaion implements recentSearchService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	recentSearchRepository recentSearchRepository; 
	

	@Override
	public Set<recentSearch> getRecentSearchesByUserEmail(String email) {
		Optional<user> userOptional = userRepository.findByEmail(email);
		return userOptional.map(user::getRecentSearches).orElse(null);
	}

	@Override
	public boolean addRecentSearch(String userEmail, String searchTerm){
		Optional<user> userOptional = userRepository.findByEmail(userEmail);
		if(userOptional.isPresent()){
			user user = userOptional.get();
			
			RecentSearchId recentSearchId = new RecentSearchId();
			recentSearchId.setUserId(user.getId());
			recentSearchId.setSearchTerm(searchTerm);
			
			recentSearch newSearch = new recentSearch();
			newSearch.setId(recentSearchId);
			newSearch.setUser(user);
			
			user.getRecentSearches().add(newSearch);
			userRepository.save(user);
			return true;
		}else {
			return false;
		}
	}	

}
