package com.application.Recipe.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.application.Recipe.DTO.PostDTOCategory;
import com.application.Recipe.Models.category;

public interface CategoryService {
	
	public List<category> getAllCategories();
	
	public void removeCategory(UUID categoryId);
	
	public Optional<category> getCategoryById(UUID categoryId);
	
	public boolean addCategory(PostDTOCategory category);
}
