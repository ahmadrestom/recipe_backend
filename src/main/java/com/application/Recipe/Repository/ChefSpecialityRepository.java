package com.application.Recipe.Repository;

import com.application.Recipe.Models.ChefSpeciality;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public interface ChefSpecialityRepository extends JpaRepository<ChefSpeciality, UUID>{
	@Query("SELECT c FROM ChefSpeciality c")
    Set<ChefSpeciality> findAllAsSet();
}
