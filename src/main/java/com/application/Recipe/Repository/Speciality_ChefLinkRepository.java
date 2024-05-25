package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
import com.application.Recipe.Models.Speciality_ChefLink;

@Repository
public interface Speciality_ChefLinkRepository extends JpaRepository<Speciality_ChefLink, ChefSpecialityLinkId>{
	
}
