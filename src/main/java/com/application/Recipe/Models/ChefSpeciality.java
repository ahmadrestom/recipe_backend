package com.application.Recipe.Models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="chef_speciality")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChefSpeciality {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="speciality_id", nullable=false)
	private UUID specialityId;
	
	@Column(name="speciality")
	private String speciality;
	
	@Column(name="description")
	private String description;

}
