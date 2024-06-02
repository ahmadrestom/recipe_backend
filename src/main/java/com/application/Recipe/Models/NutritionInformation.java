package com.application.Recipe.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nutrition_information")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionInformation {
	
	@Id
    private Integer recipeId;
	
	@MapsId
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@Column(name="calories", nullable = false)
	private BigDecimal calories;
	@Column(name="total_fat", nullable = false)
	private BigDecimal total_fat;
	@Column(name="cholesterol", nullable = false)
	private BigDecimal cholesterol;
	@Column(name="carbohydrates", nullable = false)
	private BigDecimal carbohydrates;
	@Column(name="protein", nullable = false)
	private BigDecimal protein;
	@Column(name="sugar", nullable = false)
	private BigDecimal sugar;
	@Column(name="sodium", nullable = false)
	private BigDecimal sodium;
	@Column(name="fiber", nullable = false)
	private BigDecimal fiber;
	@Column(name="zinc", nullable = false)
	private BigDecimal zinc;
	@Column(name="magnesium", nullable = false)
	private BigDecimal magnesium;
	@Column(name="potassium", nullable = false)
	private BigDecimal potassium;

}
