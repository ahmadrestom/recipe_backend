package com.application.Recipe.Repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.application.Recipe.Models.chef;
import com.application.Recipe.Models.user;

@Repository
public interface ChefRepository extends JpaRepository<chef, UUID>{
	
	Optional<chef> findByEmail(String email);
	
	@Query("SELECT SIZE(c.followers) FROM chef c WHERE c.id = :chefId")
    int countFollowers(@Param("chefId") UUID chefId);
	
	@Query("SELECT SIZE(u.following) FROM user u WHERE u.id = :userId")
    int countFollowing(@Param("userId") UUID userId);

}
