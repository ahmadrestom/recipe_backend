package com.application.Recipe.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutritionInformationDTOForRecipe {
	
	private BigDecimal calories;
    private BigDecimal total_fat;
    private BigDecimal cholesterol;
    private BigDecimal carbohydrates;
    private BigDecimal protein;
    private BigDecimal sugar;
    private BigDecimal sodium;
    private BigDecimal fiber;
    private BigDecimal zinc;
    private BigDecimal magnesium;
    private BigDecimal potassium;

}
