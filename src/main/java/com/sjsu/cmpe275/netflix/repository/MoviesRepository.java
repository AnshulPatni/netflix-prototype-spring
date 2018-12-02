package com.sjsu.cmpe275.netflix.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.sjsu.cmpe275.netflix.model.MoviesModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface MoviesRepository extends CrudRepository<MoviesModel, Integer> {

	
	@Query("SELECT m FROM MoviesModel m WHERE CONCAT(m.title, '', m.synopsis, '', m.actors, '', m.director) LIKE %?1%")
//	List<MoviesModel> filterMovies(@Param("keyword") String keyword);
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
	@Query(value = "INSERT into movies (title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movie_url) VALUES (:title, :genre, :year, :studio, :synopsis, :imageUrl, :actors, :director, :country, :rating, :availability, :price, :movieUrl)", nativeQuery = true)
	void addMovieAdmin(@Param("title") String title, @Param("genre") String genre, @Param("year") int year ,@Param("studio") String studio,@Param("synopsis") String synopsis ,@Param("imageUrl") String imageUrl, @Param("actors") String actors,@Param("director") String director, @Param("country") String country, @Param("rating") String rating, @Param("availability") String availability, @Param("price") int price, @Param("movieUrl") String movieUrl);

//
//	@Query("SELECT m FROM MoviesModel m WHERE CONCAT(m.genre, m.year, m.actors, m.director, m.rating, m.avgStars) LIKE %?1%")
//	List<MoviesModel> filterMovies(@Param("genre") String genre, @Param("year") int year, @Param("actors") String actors, @Param("director") String director, @Param("rating") String rating,  @Param("avgStars") float avgStars);

//	@Query("DELIMITER $$"
//			+ "CREATE PROCEDURE search_movies_new (@movieGenre  varchar(255),@movieActors varchar(255),@movieDirector varchar(255),@movieYear int,"
//			+ "@movieRating varchar(255),@movieAvgStars float)"
//			+ "BEGIN SET movieYear = 2013"
//			+ "SELECT title FROM movies WHERE  (genre = @movieGenre OR @movieGenre IS NULL) AND  (actors = @movieActors OR @movieActors IS NULL)"
//			+ " AND  (director = @movieDirector OR @movieDirector IS NULL) AND  (year = @movieYear OR @movieYear IS NULL)"
//			+ "AND  (rating = @movieRating OR @movieRating IS NULL) AND  (avg_stars = @movieAvgStars OR @movieAvgStars IS NULL)"
//			+ "END$$"
//			+ "DELIMITER"
//			+ "CALL search_movies_8(NULL, NULL, NULL, NULL, NULL, NULL)")
//	List<MoviesModel> filterMovies(@Param("genre") String genre, @Param("actors") String actors, @Param("director") String director, @Param("year") int year, @Param("rating") String rating,  @Param("avgStars") float avgStars);	

//	@Query("SELECT IFNULL((SELECT m FROM MoviesModel m WHERE m.genre) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.year) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.actors) , SELECT * FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.director) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.rating) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.avgStars) , SELECT * FROM MoviesModel m)")
//	List<MoviesModel> filterMovies(@Param("genre") String genre, @Param("year") int year, @Param("actors") String actors, @Param("director") String director, @Param("rating") String rating,  @Param("avgStars") float avgStars);


//	@Query("SELECT ALL FROM MoviesModel m WHERE m.genre INTERSECT SELECT ALL FROM MoviesModel m WHERE m.year INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.actors) , SELECT * FROM MoviesModel) INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.director) , SELECT * FROM MoviesModel) INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.rating) , SELECT * FROM MoviesModel) INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.avgStars) , SELECT * FROM MoviesModel)")
//	List<MoviesModel> filterMovies(@Param("genre") String genre, @Param("year") int year, @Param("actors") String actors, @Param("director") String director, @Param("rating") String rating,  @Param("avgStars") float avgStars);

	@Modifying
	@Query("UPDATE MoviesModel m set m.title = :title, m.genre = :genre, m.year = :year, m.studio = :studio, m.synopsis = :synopsis, m.imageUrl = :imageUrl, m.actors = :actors, m.director = :director, m.country = :country, m.rating = :rating,  m.availability = :availability, m.price = :price, m.movieUrl = :movieUrl WHERE m.title = :title")
	void editMovieAdmin(@Param("title") String title, @Param("genre") String genre, @Param("year") int year ,@Param("studio") String studio,@Param("synopsis") String synopsis ,@Param("imageUrl") String imageUrl, @Param("actors") String actors,@Param("director") String director, @Param("country") String country, @Param("rating") String rating, @Param("availability") String availability, @Param("price") int price, @Param("movieUrl") String movieUrl);

	//@Procedure(name = "filter_procedure1")

//	SELECT title
//	FROM   netflix.movies
//	WHERE  (genre = movieGenre OR movieGenre IS NULL)
//	AND  (year = movieYear OR movieYear IS NULL)
//	AND  (actors = movieActors OR movieActors IS NULL)
//	AND  (director = movieDirector OR movieDirector IS NULL)
//	AND  (rating = movieRating OR movieRating IS NULL)
//	AND  (avg_stars = movieAvgStars OR movieAvgStars IS NULL);

//    @Query("SELECT m.title, m.genre, m.year, m.studio, m.synopsis, m.imageUrl, m.actors, m.director, m.country, m.rating, m.availability, m.avgStars, m.noOfReviews FROM MoviesModel m WHERE (m.genre = :genre OR :genre is NULL ) AND (m.year = :year OR :year = 0) AND (m.actors = :actors OR :actors IS NULL) AND (m.director = :director OR :director IS NULL) AND (m.rating = :rating OR :rating IS NULL) AND (m.avgStars = :avgStars OR :avgStars = 0.0)")
	//@Query(value = "SELECT m.title FROM MoviesModel m WHERE m.genre = genre OR genre IS NULL ", nativeQuery = true)
	@Query("SELECT m FROM MoviesModel m WHERE (m.genre = :genre OR :genre is NULL ) AND (m.year = :year OR :year = 0) AND (m.actors = :actors OR :actors IS NULL) AND (m.director = :director OR :director IS NULL) AND (m.rating = :rating OR :rating IS NULL) AND (m.avgStars > :avgStars OR :avgStars = 0.0)")
	List<MoviesModel> filterBY(@Param("genre") String genre, @Param("year") int year, @Param("actors") String actors, @Param("director") String director, @Param("rating") String rating,  @Param("avgStars") float avgStars);


//	@Modifying
//	@Query("UPDATE MoviesModel s set s.star_rating = :star_rating WHERE s.email = :email")
//	void updateSubscriptionStartDate(@Param("email") String email, @Param("subscriptionStartDate") Date subscriptionStartDate);



	@Modifying
	@Query("Delete FROM MoviesModel m WHERE m.title =:title")
	void delMovieByAdmin(@Param("title") String title);

	
}


