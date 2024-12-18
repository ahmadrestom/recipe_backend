package com.application.Recipe.ServiceImplementation;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Recipe.Models.ChefSpeciality;
import com.application.Recipe.Repository.ChefSpecialityRepository;
import com.application.Recipe.Services.ChefSpecialityService;

@Service
public class ChefSpecialityServiceImplementation implements ChefSpecialityService{

	@Autowired
	private ChefSpecialityRepository chefSpecialityRepository;
	
	
	
	@Override
	public boolean addSpeciality(ChefSpeciality chefSpeciality) {
		ChefSpeciality savedSpeciality = chefSpecialityRepository.save(chefSpeciality);
	    return savedSpeciality != null;
	}

	@Override
	public Set<ChefSpeciality> getAllSpecialities() {
		return chefSpecialityRepository.findAllAsSet();
	}

	@Override
	public boolean deleteSpeciality(ChefSpeciality chefSpeciality) {
		UUID id = chefSpeciality.getSpecialityId();
		if(chefSpecialityRepository.existsById(id)) {
			chefSpecialityRepository.deleteById(id);
			return !chefSpecialityRepository.existsById(id);
		}
		return false;
	}
}
