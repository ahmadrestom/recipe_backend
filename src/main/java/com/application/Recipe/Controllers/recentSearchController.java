package com.application.Recipe.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.Models.recentSearch;
import com.application.Recipe.Services.UserService;
import com.application.Recipe.Services.recentSearchService;

@RestController
@RequestMapping("/api/v1/recentSearches")
public class recentSearchController {
	
	@Autowired
	private recentSearchService recentSearchService;
	
	private UserService userService;
	
	@GetMapping("/getUserSearches")
	public ResponseEntity<Set<recentSearch>> getUserRecentSearches(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = userDetails.getUsername();
		Set<recentSearch> recentSearches = recentSearchService.getRecentSearchesByUserEmail(userEmail);
		if(recentSearches != null) {
			return ResponseEntity.ok(recentSearches);
		}else {
			return ResponseEntity.notFound().build();
		}	
	}
	
	static class SearchTermRequest{
		public String search_term;
	}
	
	@PostMapping("/addUserSearch")
	public ResponseEntity<String> addRecentSearch(@RequestBody SearchTermRequest searchTermRequest){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = userDetails.getUsername();
		boolean isAdded = recentSearchService.addRecentSearch(userEmail, searchTermRequest.search_term);
		if(isAdded) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Recent search added successfully");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding recent search");
		}
	}

}
