package com.application.Recipe.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.Models.ChefSpeciality;
import com.application.Recipe.Services.ChefSpecialityService;

@RestController
@RequestMapping("/api/v1/chefSpeciality")
public class chefSpecialityController {
	
	@Autowired
	private ChefSpecialityService chefSpecialityService;
	
	@GetMapping("/getAllSpecialities")
	public ResponseEntity<Set<ChefSpeciality>> getAllSpecialties(){
		
		try {
			Set<ChefSpeciality> chefSpecialities= chefSpecialityService.getAllSpecialities();
			if(chefSpecialities.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(chefSpecialities);
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/addChefSpeciality")
	public ResponseEntity<ChefSpeciality> addNewSpeciality(@RequestBody ChefSpeciality chefSpeciality){
		try {
			boolean isAdded = chefSpecialityService.addSpeciality(chefSpeciality);
			if(isAdded) {
				return ResponseEntity.status(HttpStatus.CREATED).body(chefSpeciality);
			}else {
				return ResponseEntity.noContent().build();
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}	
	}
	
	@DeleteMapping("/deleteSpeciality")
	public ResponseEntity<ChefSpeciality> removeSpeciality(@RequestBody ChefSpeciality chefSpeciality){
		try {
			boolean isRemoved = chefSpecialityService.deleteSpeciality(chefSpeciality);
			if(isRemoved) {
				return ResponseEntity.ok(chefSpeciality);
			}else {
				return ResponseEntity.notFound().build();
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	

}
