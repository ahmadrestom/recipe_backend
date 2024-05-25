package com.application.Recipe.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
import com.application.Recipe.Models.Speciality_ChefLink;
import com.application.Recipe.Services.SpecialityChefLinkService;

@RestController
@RequestMapping("/api/v2/user/specificChefSpeciality")
public class SpecialityChefLinkController {
	
	
	@Autowired
	private SpecialityChefLinkService specialityChefLinkService;
	
	@PostMapping("/addLink")
	public ResponseEntity<Speciality_ChefLink> addLink(@RequestBody Map<String, Map<String, Integer>> request){
		try {
			Integer chefId = request.get("chef").get("chefId");
			Integer specialityId = request.get("chefSpeciality").get("specialityId");
			Speciality_ChefLink createdlink = specialityChefLinkService.addLink(chefId, specialityId);
			if(createdlink!=null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(createdlink);
			}else {
				return ResponseEntity.noContent().build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/getAllLinks")
	public ResponseEntity<List<Speciality_ChefLink>> getAllLinks(){
		try {
			List<Speciality_ChefLink> links = specialityChefLinkService.allLinks();
			if(links.isEmpty())
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(links);
						
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/deleteLink")
	public ResponseEntity<Void> deleteLink(@RequestBody Map<String, Integer> request){
		try {
			Integer chefId = request.get("chefId");
			Integer specialityId = request.get("specialityId");
			
			ChefSpecialityLinkId id = new ChefSpecialityLinkId(chefId, specialityId);			
			specialityChefLinkService.removeLink(id);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
