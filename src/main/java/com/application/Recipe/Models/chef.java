package com.application.Recipe.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="chef")
@PrimaryKeyJoinColumn(name="user_id")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class chef extends user{

	private static final long serialVersionUID = 1L;
	
	
	@Column(name= "location", nullable = false)
	private String location;
	
	@Column(name= "phone_number", nullable = false)
	private String phone_number;
	
	@Column(name= "bio", nullable = false)
	private String bio;
	
	@Column(name= "years_experience", nullable = false)
	private Integer years_experience;

}
