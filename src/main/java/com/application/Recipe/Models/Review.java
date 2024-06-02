package com.application.Recipe.Models;

import java.time.LocalDateTime;
import com.application.Recipe.CompositeKeys.ReviewId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="reviews")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Review {
	
	@EmbeddedId
	private ReviewId id;
	@Column(name="text", nullable=false)
	private String text;
	@Column(name="likes", nullable=false)
	private Integer likes;
	@Column(name="dislikes", nullable=false)
	private Integer dislikes;
	@Column(name="time_uploaded", nullable=false)
	private LocalDateTime timeUploaded;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	private user user;
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("recipeId")
	@JoinColumn(name="recipe_id", insertable = false, updatable = false)
	private Recipe recipe;
	
	@PrePersist
    protected void onCreate() {
        timeUploaded = LocalDateTime.now();
        likes = 0;
        dislikes = 0;
    }
	
	

}
