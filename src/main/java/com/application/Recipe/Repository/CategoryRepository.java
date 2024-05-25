package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.Recipe.Models.category;

@Repository
public interface CategoryRepository extends JpaRepository<category, Integer>{

}
