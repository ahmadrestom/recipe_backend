package com.application.Recipe.Models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	
	@OneToMany(mappedBy = "chef", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Recipe> recipes;
	
	@ManyToMany
	@JoinTable(
			name="followers",
			joinColumns = @JoinColumn(name="chef_id"),
			inverseJoinColumns = @JoinColumn(name="user_id")	
		)
	private Set<user> followers;
	
	

}
