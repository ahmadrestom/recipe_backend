package com.application.Recipe.Services;


import java.util.List;
import java.util.UUID;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
import com.application.Recipe.DTO.SpecialityLinkDTO;
import com.application.Recipe.Models.Speciality_ChefLink;

public interface SpecialityChefLinkService {
	public Speciality_ChefLink addLink(UUID chefId, UUID chefSpecialityId);
	public List<SpecialityLinkDTO> allLinksForChef(UUID chefId);
	public void removeLink(ChefSpecialityLinkId id);
	
}
