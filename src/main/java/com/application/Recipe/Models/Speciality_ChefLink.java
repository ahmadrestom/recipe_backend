package com.application.Recipe.Models;

import com.application.Recipe.CompositeKeys.ChefSpecialityLinkId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="chef_speciality_link")
public class Speciality_ChefLink {
	
	
	@EmbeddedId
	private ChefSpecialityLinkId id;
	
	@ManyToOne
	@MapsId("chefId")
	@JoinColumn(name = "chef_id")
	private chef chef;
	
	@ManyToOne
	@MapsId("specialityId")
	@JoinColumn(name = "speciality_id")
	private ChefSpeciality chefSpeciality;

}
