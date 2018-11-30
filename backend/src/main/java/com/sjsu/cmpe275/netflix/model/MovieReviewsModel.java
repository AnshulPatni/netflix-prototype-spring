package com.sjsu.cmpe275.netflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="movie_reviews")
public class MovieReviewsModel {

	@Id
	@Column(name="movie_review_id")
	private int movieReviewId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="review_rating")
	private int reviewRating;
	
	@Column(name="review")
	private String review;
	
	public MovieReviewsModel() {
		
	}

	public MovieReviewsModel(int movieReviewId, String title, int reviewRating, String review) {
		super();
		this.movieReviewId = movieReviewId;
		this.title = title;
		this.reviewRating = reviewRating;
		this.review = review;
	}

	public int getMovieReviewId() {
		return movieReviewId;
	}

	public void setMovieReviewId(int movieReviewId) {
		this.movieReviewId = movieReviewId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(int reviewRating) {
		this.reviewRating = reviewRating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
}
