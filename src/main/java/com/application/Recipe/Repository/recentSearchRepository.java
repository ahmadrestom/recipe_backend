package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Recipe.CompositeKeys.RecentSearchId;
import com.application.Recipe.Models.recentSearch;

public interface recentSearchRepository extends JpaRepository<recentSearch, RecentSearchId>{

}
