package com.application.Recipe.ServiceImplementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.DTO.PostDTOCategory;
import com.application.Recipe.Models.category;
import com.application.Recipe.Repository.CategoryRepository;
import com.application.Recipe.Services.CategoryService;

@Service
public class CategoryServiceImplementation implements CategoryService{

	@Autowired
	CategoryRepository categoryRepository; 
	
	
	
	@Override
	public List<category> getAllCategories() {
		List<category> allCategories = categoryRepository.findAll();
		if(allCategories.isEmpty()) {
			throw new RuntimeException("No categories found");
		}else {
			return allCategories;
		}
	}

	@Override
	public void removeCategory(UUID categoryId) {
		if (!categoryRepository.existsById(categoryId)) {
	        throw new RuntimeException("Category not found");
	    }
	categoryRepository.deleteById(categoryId);
	}

	@Override
	public Optional<category> getCategoryById(UUID categoryId) {
		Optional<category> category = categoryRepository.findById(categoryId);
		if(category.isPresent()) {
			return category;
		}else {
			throw new RuntimeException("Category not found");			
		}
	}

	@Override
	public boolean addCategory(PostDTOCategory categoryDTO) {
	    category category = new category();
	    category.setCategoryName(categoryDTO.getCategory_name());
	    category savedCategory = categoryRepository.save(category);
	    return savedCategory != null;
	}

}
