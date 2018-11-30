package com.sjsu.cmpe275.netflix.repository;

import java.beans.Transient;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.netflix.model.MovieReviewsModel;

@Repository
@Transactional
public interface MovieReviewsRepository extends CrudRepository<MovieReviewsModel, Integer> {
	
	@Modifying
    @Query(value = "INSERT into movie_reviews (title, review_rating, review) VALUES (:title,:reviewRating,:review)", nativeQuery = true)
    @Transactional
    void insertMovieReview(@Param("title") String title, @Param("reviewRating") int reviewRating, @Param("review") String review);
	
	
	
	@Transient
	@Query("SELECT u.title FROM MovieReviewsModel u GROUP BY u.title ORDER BY CAST(SUM(u.reviewRating) AS float)/CAST(count(*) AS float) DESC")
	List getScoreCardByStar();
}
