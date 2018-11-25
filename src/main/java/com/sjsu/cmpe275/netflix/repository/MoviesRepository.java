package com.sjsu.cmpe275.netflix.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sjsu.cmpe275.netflix.model.Movies;

@Repository
@Transactional
public interface MoviesRepository extends CrudRepository<Movies, Integer> {
	
	@Query("SELECT m FROM Movies m WHERE CONCAT(m.title, '', m.synopsis, '', m.actors, '', m.director) LIKE %?1%")
	List<Movies> getMoviesByKeyword(@Param("keyword") String keyword);
	
}
