package com.application.Recipe.Services;

import java.util.List;
import java.util.Optional;

import com.application.Recipe.Models.category;

public interface CategoryService {
	
	public List<category> getAllCategories();
	
	public void removeCategory(Integer categoryId);
	
	public Optional<category> getCategoryById(Integer categoryId);
	
	public boolean addCategory(category category);
}
