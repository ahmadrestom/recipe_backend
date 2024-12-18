package com.application.Recipe.Models;

import java.io.Serializable;

import com.application.Recipe.CompositeKeys.IngredientId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ingredients")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ingredient implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private IngredientId id;
	
	@Column(name="grams")
	private Integer grams;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("recipeId")
	@JoinColumn(name = "recipe_id", insertable = false, updatable = false)
	private Recipe recipe;

}
