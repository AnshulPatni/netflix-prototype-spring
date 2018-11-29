package com.sjsu.cmpe275.netflix.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sjsu.cmpe275.netflix.model.MoviesModel;

@Repository
@Transactional
public interface MoviesRepository extends CrudRepository<MoviesModel, Integer> {
	
	@Query("SELECT m FROM MoviesModel m WHERE CONCAT(m.title, '', m.synopsis, '', m.actors, '', m.director) LIKE %?1%")
	List<MoviesModel> getMoviesByKeyword(@Param("keyword") String keyword);
	
	@Query("SELECT m FROM MoviesModel m WHERE m.title = :title")
	MoviesModel getMovieDetails(@Param("title") String title);
	
	@Query("SELECT m.availability FROM MoviesModel m WHERE m.title = :title")
	String getMovieAvailabilityByTitle(@Param("title") String title);
	
	@Query("SELECT m.price FROM MoviesModel m WHERE m.title = :title")
	int getMoviePrice(@Param("title") String title);
	
	@Query("SELECT m.avgStars FROM MoviesModel m WHERE m.title = :title")
	float getAvgStars(@Param("title") String title);
	
	@Query("SELECT m.noOfReviews FROM MoviesModel m WHERE m.title = :title")
	int getNoOfReviews(@Param("title") String title);
	
	@Query("SELECT m FROM MoviesModel m")
	List<MoviesModel> getAllMovies();
	
	@Modifying
	@Query("UPDATE MoviesModel m set m.avgStars = :avgStars, m.noOfReviews = :noOfReviews WHERE m.title = :title")
	void updateAvgStarsAndReviewCount(@Param("title") String title, @Param("avgStars") float avgStars, @Param("noOfReviews") int noOfReviews);

	
	
	@Modifying
	@Query(value = "INSERT into movies (title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movie_url) VALUES (:title, :genre, :year, :studio, :synopsis, :image_url, :actors, :director, :country, :rating, :availability, :price, :movie_url)", nativeQuery = true)
	void addMovieAdmin(@Param("title") String title, @Param("genre") String genre, @Param("year") int year ,@Param("studio") String studio,@Param("synopsis") String synopsis ,@Param("image_url") String image_url, @Param("actors") String actors,@Param("director") String director, @Param("country") String country, @Param("rating") String rating, @Param("availability") String availability, @Param("price") int price, @Param("movie_url") String movie_url);
}