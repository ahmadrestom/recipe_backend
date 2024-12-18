package com.application.Recipe.Services;

import java.util.Set;

import com.application.Recipe.Models.recentSearch;

public interface recentSearchService {
	
	
	public Set<recentSearch> getRecentSearchesByUserEmail(String email);
	public boolean addRecentSearch(String userEmail, String searchTerm);

}
