package com.sjsu.cmpe275.netflix.model;

import javax.persistence.*;

@Entity
@Table(name="movies")
public class MoviesModel {

	@Id
	@Column(name="title")
	private String title;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name="year")
	private int year;
	
	@Column(name="studio")
	private String studio;
	
	@Column(name="synopsis")
	private String synopsis;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="actors")
	private String actors;
	
	@Column(name="director")
	private String director;
	
	@Column(name="country")
	private String country;
	
	@Column(name="rating")
	private String rating;
	
	@Column(name="availability")
	private String availability;
	
	@Column(name="price")
	private int price;
	
	@Column(name="avg_stars")
	private float avgStars;
	
	@Column(name="no_of_reviews")
	private int noOfReviews;


	@Column(name = "movie_url")
	private String movieUrl;
	
	
	public String getMovieUrl() {
		return movieUrl;
	}

	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}


	public MoviesModel() {
		
	}
	
	public MoviesModel(String title, String genre, int year, String studio, String synopsis, String imageUrl,
			String actors, String director, String country, String rating, String availability, int price,
			float avgStars, int noOfReviews, String movieUrl) {
		super();
		this.title = title;
		this.genre = genre;
		this.year = year;
		this.studio = studio;
		this.synopsis = synopsis;
		this.imageUrl = imageUrl;
		this.actors = actors;
		this.director = director;
		this.country = country;
		this.rating = rating;
		this.availability = availability;
		this.price = price;
		this.avgStars = avgStars;
		this.noOfReviews = noOfReviews;
		this.movieUrl = movieUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public float getAvgStars() {
		return avgStars;
	}

	public void setAvgStars(float avgStars) {
		this.avgStars = avgStars;
	}

	public int getNoOfReviews() {
		return noOfReviews;
	}

	public void setNoOfReviews(int noOfReviews) {
		this.noOfReviews = noOfReviews;
	}
	
}