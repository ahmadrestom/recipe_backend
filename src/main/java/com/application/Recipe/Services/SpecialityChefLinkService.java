package com.application.Recipe.Services;


import java.util.List;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
import com.application.Recipe.Models.Speciality_ChefLink;

public interface SpecialityChefLinkService {
	public Speciality_ChefLink addLink(Integer chefId, Integer chefSpecialityId);
	public List<Speciality_ChefLink> allLinks();
	public void removeLink(ChefSpecialityLinkId id);
	
}
