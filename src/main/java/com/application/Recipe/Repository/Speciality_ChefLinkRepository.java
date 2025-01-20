package com.application.Recipe.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;
import com.application.Recipe.Models.Speciality_ChefLink;

@Repository
public interface Speciality_ChefLinkRepository extends JpaRepository<Speciality_ChefLink, ChefSpecialityLinkId>{
	@Query("SELECT l FROM Speciality_ChefLink l WHERE l.id.chefId = :chefId")
	List<Speciality_ChefLink> findByChefId(@Param("chefId") UUID chefId);

	
}
