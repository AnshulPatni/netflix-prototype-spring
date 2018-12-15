package com.sjsu.cmpe275.netflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recommendation")
public class RecommendationModel {

	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="keyword")
	private String keyword;
	
	@Column(name="actors")
	private String actors;
	
	@Column(name="genre")
	private String genre;
	

	@Column(name="director")
	private String director;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getActors() {
		return actors;
	}


	public void setActors(String actors) {
		this.actors = actors;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getDirector() {
		return director;
	}


	public void setDirector(String director) {
		this.director = director;
	}


	public RecommendationModel(String email, String keyword, String actors, String genre, String director) {
		super();
		this.email = email;
		this.keyword = keyword;
		this.actors = actors;
		this.genre = genre;
		this.director = director;
	}


	public RecommendationModel() {
	}
	
	
}
