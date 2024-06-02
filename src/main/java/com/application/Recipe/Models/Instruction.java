package com.application.Recipe.Models;

import com.application.Recipe.CompositeKeys.InstructionId;

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
@Table(name="instructions")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instruction {
	
	@EmbeddedId
	private InstructionId id;

	@Column(name="instruction")
	private String instruction;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("recipeId")
	@JoinColumn(name="recipe_id",insertable = false, updatable = false)
	private Recipe recipe;
	

}
