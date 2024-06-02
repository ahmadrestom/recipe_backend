package com.application.Recipe.Models;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="speciality_id", nullable=false)
	private Integer specialityId;
	
	@Column(name="speciality")
	private String speciality;

}
