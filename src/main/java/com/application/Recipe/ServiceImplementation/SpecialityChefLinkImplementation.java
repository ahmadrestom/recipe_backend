package com.application.Recipe.ServiceImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
import com.application.Recipe.DTO.SpecialityLinkDTO;
import com.application.Recipe.Models.ChefSpeciality;
import com.application.Recipe.Models.Speciality_ChefLink;
import com.application.Recipe.Models.chef;
import com.application.Recipe.Repository.ChefRepository;
import com.application.Recipe.Repository.ChefSpecialityRepository;
import com.application.Recipe.Repository.Speciality_ChefLinkRepository;
import com.application.Recipe.Services.SpecialityChefLinkService;

@Service
public class SpecialityChefLinkImplementation implements SpecialityChefLinkService{

	@Autowired
	private Speciality_ChefLinkRepository speciality_ChefLinkRepository;
	
	@Autowired
    private ChefRepository chefRepository;
	
	@Autowired
    private ChefSpecialityRepository chefSpecialityRepository;
	
	
	@Override
    public Speciality_ChefLink addLink(UUID chefId, UUID specialityId) {
        chef chefEntity = chefRepository.findById(chefId)
                .orElseThrow(() -> new RuntimeException("Chef not found"));
        ChefSpeciality specialityEntity = chefSpecialityRepository.findById(specialityId)
                .orElseThrow(() -> new RuntimeException("Speciality not found"));

        ChefSpecialityLinkId id = new ChefSpecialityLinkId(chefId, specialityId);
        if (speciality_ChefLinkRepository.existsById(id)) {
            throw new IllegalStateException("Link already exists");
        }

        Speciality_ChefLink link = new Speciality_ChefLink(id, chefEntity, specialityEntity);
        return speciality_ChefLinkRepository.save(link);
    }

	@Override
	public List<SpecialityLinkDTO> allLinksForChef(UUID chefId) {
	    List<Speciality_ChefLink> links = speciality_ChefLinkRepository.findByChefId(chefId);

	    List<SpecialityLinkDTO> specialityDTOs = new ArrayList<>();
	    
	    for (Speciality_ChefLink link : links) {
	        SpecialityLinkDTO specialityDTO = SpecialityLinkDTO.builder()
	            .specialityId(link.getChefSpeciality().getSpecialityId()) // Get the speciality ID
	            .speciality(link.getChefSpeciality().getSpeciality()) // Get the speciality name
	            .build();

	        specialityDTOs.add(specialityDTO);
	    }

	    return specialityDTOs;
	}




	@Override
	public void removeLink(ChefSpecialityLinkId id){
		speciality_ChefLinkRepository.deleteById(id);
	}

}
