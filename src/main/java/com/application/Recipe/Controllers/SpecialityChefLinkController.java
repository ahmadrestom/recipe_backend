package com.application.Recipe.Controllers;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
import com.application.Recipe.DTO.SpecialityLinkDTO;
import com.application.Recipe.Models.Speciality_ChefLink;
import com.application.Recipe.Services.SpecialityChefLinkService;

@RestController
@RequestMapping("/api/v2/user/specificChefSpeciality")
public class SpecialityChefLinkController {
	
	
	@Autowired
	private SpecialityChefLinkService specialityChefLinkService;
	
	@PostMapping("/addLink/{chefId}/{specialityId}")
    public ResponseEntity<Speciality_ChefLink> addLink(@PathVariable UUID chefId, @PathVariable UUID specialityId) {
        try {
            Speciality_ChefLink link = specialityChefLinkService.addLink(chefId, specialityId);
            return ResponseEntity.status(HttpStatus.CREATED).body(link);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
	@GetMapping("/getAllLinks/{chefId}")
	public ResponseEntity<List<SpecialityLinkDTO>> getAllLinks(@PathVariable UUID chefId){
		try {
			List<SpecialityLinkDTO> links = specialityChefLinkService.allLinksForChef(chefId);
			if(links.isEmpty())
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(links);
						
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/deleteLink")
	public ResponseEntity<Void> deleteLink(@RequestBody Map<String, String> request){
		try {
			UUID chefId = UUID.fromString(request.get("chefId"));
			UUID specialityId = UUID.fromString(request.get("specialityId"));
			
			ChefSpecialityLinkId id = new ChefSpecialityLinkId(chefId, specialityId);			
			specialityChefLinkService.removeLink(id);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
