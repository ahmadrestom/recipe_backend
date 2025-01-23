package com.application.Recipe.Models;

import com.application.Recipe.CompositeKeys.FollowId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Table(name="followers")
@Entity
@AllArgsConstructor
public class Follow {

	@EmbeddedId
    private FollowId id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private chef chef;

    // Constructors, getters, setters
}