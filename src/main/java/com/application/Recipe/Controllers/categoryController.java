package com.application.Recipe.Controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.DTO.PostDTOCategory;
import com.application.Recipe.Models.category;
import com.application.Recipe.Services.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class categoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/getAllCategories")
	public ResponseEntity<List<category>> getAllCategories(){
		try {
			List<category> categories = categoryService.getAllCategories();
			if(categories.isEmpty()) {
				return ResponseEntity.notFound().build();
			}else {
				return ResponseEntity.ok(categories);
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/getCategoryById/{categoryId}")
	public ResponseEntity<category> getCategoryById(@PathVariable UUID categoryId){
		try {
			category c = categoryService.getCategoryById(categoryId)
					.orElseThrow(()->new RuntimeException("Category not found"));
			return ResponseEntity.ok(c);
			
		}catch(NoSuchElementException e) {
			return ResponseEntity.notFound().build();
			
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/addCategory")
	public ResponseEntity<PostDTOCategory> addCategory(@RequestBody PostDTOCategory category){
		
		try {
			boolean isAdded = categoryService.addCategory(category);
			return isAdded? ResponseEntity.status(HttpStatus.CREATED).body(category):ResponseEntity.badRequest().build();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId){
		categoryService.removeCategory(categoryId);
		return ResponseEntity.ok().build();
	}
	
	

}
