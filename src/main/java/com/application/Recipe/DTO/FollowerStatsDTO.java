package com.application.Recipe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowerStatsDTO {
	private Integer followerCount;
	private int followingCount;
	
	public FollowerStatsDTO(int followingNumber){
		this.followingCount = followingNumber;
	}
}