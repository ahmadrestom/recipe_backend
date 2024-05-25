package com.application.Recipe.ServiceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
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
	public Speciality_ChefLink addLink(Integer chefId, Integer chefSpecialityId){
		chef c  = chefRepository.findById(chefId)
				.orElseThrow(()->new RuntimeException("Chef not found"));
		ChefSpeciality cs = chefSpecialityRepository.findById(chefSpecialityId)
				.orElseThrow(()->new RuntimeException("Speciality not found"));
		
		ChefSpecialityLinkId id = new ChefSpecialityLinkId(chefId, chefSpecialityId);
		Speciality_ChefLink link = new Speciality_ChefLink(id, c, cs);
		return speciality_ChefLinkRepository.save(link);
	}

	@Override
	public List<Speciality_ChefLink> allLinks() {
		return speciality_ChefLinkRepository.findAll();
	}

	@Override
	public void removeLink(ChefSpecialityLinkId id){
		speciality_ChefLinkRepository.deleteById(id);
	}

}
