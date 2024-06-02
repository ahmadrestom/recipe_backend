package com.application.Recipe.CompositeKeys;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Embeddable
public class ReviewId implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="recipe_id")
	private Integer recipeId;
	@Column(name="user_id")
	private Integer userId;
}
