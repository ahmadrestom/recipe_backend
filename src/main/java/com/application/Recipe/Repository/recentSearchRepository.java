package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.Recipe.CompositeKeys.RecentSearchId;
import com.application.Recipe.Models.recentSearch;

@Repository
public interface recentSearchRepository extends JpaRepository<recentSearch, RecentSearchId>{

}
