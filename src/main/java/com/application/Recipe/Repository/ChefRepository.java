package com.application.Recipe.Repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.Recipe.Models.chef;

@Repository
public interface ChefRepository extends JpaRepository<chef, UUID>{

}
