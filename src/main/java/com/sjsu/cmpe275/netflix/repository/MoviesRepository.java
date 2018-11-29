package com.sjsu.cmpe275.netflix.repository;

import java.math.BigDecimal;

import java.sql.Date;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.sjsu.cmpe275.netflix.model.MoviesModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sjsu.cmpe275.netflix.model.MoviesModel;
import org.springframework.web.bind.annotation.RequestParam;

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
	@Query(value = "INSERT into movies (title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movie_url) VALUES (:title, :genre, :year, :studio, :synopsis, :image_url, :actors, :director, :country, :rating, :availability, :price, :movie_url)", nativeQuery = true)
	void addMovieAdmin(@Param("title") String title, @Param("genre") String genre, @Param("year") int year ,@Param("studio") String studio,@Param("synopsis") String synopsis ,@Param("image_url") String image_url, @Param("actors") String actors,@Param("director") String director, @Param("country") String country, @Param("rating") String rating, @Param("availability") String availability, @Param("price") int price, @Param("movie_url") String movie_url);

//
	@Query("SELECT m FROM MoviesModel m WHERE CONCAT(m.genre, m.year, m.actors, m.director, m.rating, m.avgStars) LIKE %?1%")
	List<MoviesModel> filterMovies(@Param("genre") String genre, @Param("year") int year, @Param("actors") String actors, @Param("director") String director, @Param("rating") String rating,  @Param("avgStars") float avgStars);


//	@Query("SELECT IFNULL((SELECT m FROM MoviesModel m WHERE m.genre) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.year) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.actors) , SELECT * FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.director) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.rating) , SELECT m FROM MoviesModel m) INTERSECT SELECT IFNULL( (SELECT m FROM MoviesModel m WHERE m.avgStars) , SELECT * FROM MoviesModel m)")
//	List<MoviesModel> filterMovies(@Param("genre") String genre, @Param("year") int year, @Param("actors") String actors, @Param("director") String director, @Param("rating") String rating,  @Param("avgStars") float avgStars);


//	@Query("SELECT ALL FROM MoviesModel m WHERE m.genre INTERSECT SELECT ALL FROM MoviesModel m WHERE m.year INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.actors) , SELECT * FROM MoviesModel) INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.director) , SELECT * FROM MoviesModel) INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.rating) , SELECT * FROM MoviesModel) INTERSECT SELECT IFNULL( (SELECT * FROM MoviesModel m WHERE m.avgStars) , SELECT * FROM MoviesModel)")
//	List<MoviesModel> filterMovies(@Param("genre") String genre, @Param("year") int year, @Param("actors") String actors, @Param("director") String director, @Param("rating") String rating,  @Param("avgStars") float avgStars);




	//			( SELECT *
//			FROM MoviesModel m
//			WHERE (number = '1101'
//					OR number = '481101')
//			UNION ALL
//			SELECT *
//					FROM dynamicsql PARTITION(dynamicsql_200012b)
//			WHERE (number = '1101'
//					OR number = '481101')
//			);
//			FROM"MoviesModel m WHERE CONCAT(m.genre, m.year, m.actors, m.director, m.rating, m.avgStars) LIKE %?1%")

//
//	@Query("SELECT * FROM MovieModel m WHERE(m.genre IS NULL OR m.genre = @lContactID)
//	AND (@lFirstName IS NULL OR FirstName LIKE '%' + @lFirstName + '%')
//	AND (@lLastName IS NULL OR LastName LIKE '%' + @lLastName + '%')
//	AND (@lEmailAddress IS NULL OR EmailAddress LIKE '%' + @lEmailAddress + '%')
//	AND (@lEmailPromotion IS NULL OR EmailPromotion = @lEmailPromotion)
//	AND (@lPhone IS NULL OR Phone = @lPhone))
//
//	SELECT NVL(COUNT(*), 0) st
//	FROM ( SELECT *
//					FROM dynamicsql PARTITION(dynamicsql_200012a)
//	WHERE (number = '1101'
//			OR number = '481101')
//	UNION ALL
//	SELECT *
//	FROM dynamicsql PARTITION(dynamicsql_200012b)
//	WHERE (number = '1101'
//			OR number = '481101')
//       );

	//        genre, year, actors, directors, MPAA rating, and number of stars

//	@Modifying
//	@Query("UPDATE MoviesModel s set s.star_rating = :star_rating WHERE s.email = :email")
//	void updateSubscriptionStartDate(@Param("email") String email, @Param("subscriptionStartDate") Date subscriptionStartDate);



//	SELECT
//			ContactID,
//			Title,
//			FirstName,
//			MiddleName,
//			LastName,
//			Suffix,
//			EmailAddress,
//			EmailPromotion,
//			Phone
//	FROM [Person].[Contact]
//	Where
//			(@lContactID IS NULL OR ContactID = @lContactID)
//	AND (@lFirstName IS NULL OR FirstName LIKE '%' + @lFirstName + '%')
//	AND (@lLastName IS NULL OR LastName LIKE '%' + @lLastName + '%')
//	AND (@lEmailAddress IS NULL OR EmailAddress LIKE '%' + @lEmailAddress + '%')
//	AND (@lEmailPromotion IS NULL OR EmailPromotion = @lEmailPromotion)
//	AND (@lPhone IS NULL OR Phone = @lPhone)
//	ORDER BY ContactID


}


