package com.application.Recipe.Services;

import java.util.Set;

import com.application.Recipe.Models.ChefSpeciality;

public interface ChefSpecialityService {
	
	public boolean addSpeciality(ChefSpeciality chefSpeciality);
	public Set<ChefSpeciality> getAllSpecialities();
	public boolean deleteSpeciality(ChefSpeciality chefSpeciality);
}
