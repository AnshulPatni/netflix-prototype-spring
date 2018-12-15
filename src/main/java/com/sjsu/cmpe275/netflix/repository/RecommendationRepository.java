package com.sjsu.cmpe275.netflix.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.netflix.model.RecommendationModel;

@Repository
@Transactional
public interface RecommendationRepository extends CrudRepository<RecommendationModel, Integer> {
	
	@Query("SELECT m.genre FROM RecommendationModel m WHERE m.email = :email")
	String getAlreadyuser(@Param("email") String email);
	
	@Modifying
	@Query(value = "INSERT into recommendation (email, keyword, actors, genre, director) VALUES (:email, :keyword, :actors, :genre, :director)", nativeQuery = true)
	void recommendationMoviesByKeyword(@Param("email") String email ,@Param("keyword") String keyword ,@Param("actors") String actors, @Param("genre") String genre,@Param("director") String director);
	
	@Query("SELECT m.actors, m.genre, m.director, m.keyword FROM RecommendationModel m WHERE m.email = :email")
	List<Object[]> getDatarecommendedMovies(@Param("email") String email);
	
	@Modifying
	@Query("UPDATE RecommendationModel m set m.keyword = :keyword, m.actors = :actors, m.genre = :genre, m.director = :director WHERE m.email = :email")
	void recommendationMoviesByKeywordUpdate(@Param("email") String email ,@Param("keyword") String keyword ,@Param("actors") String actors, @Param("genre") String genre,@Param("director") String director);
	
}
