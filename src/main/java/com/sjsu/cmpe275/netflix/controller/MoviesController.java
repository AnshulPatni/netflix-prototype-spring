package com.sjsu.cmpe275.netflix.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.sjsu.cmpe275.netflix.model.MoviesModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.repository.MoviesRepository;
import com.sjsu.cmpe275.netflix.model.MoviesModel;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/movies")
public class MoviesController {

	//add_movie	edit_movie,	delete_movie,	search_movie,	review_movie,	get_top_movie_on_stars
	//Movie,	Movie_review

	@Autowired
	MoviesRepository repository;

	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchMoviesByKeyword(@PathVariable("keyword") String keyword) {
		HttpStatus status = HttpStatus.OK;

		List<Map<String, String>> responseList = new ArrayList<>();
		List<MoviesModel> allMovies = repository.getMoviesByKeyword(keyword);
		
		for(MoviesModel eachMovie: allMovies) {
			Map<String, String> eachMovieMap = new HashMap<>();
			eachMovieMap.put("title", eachMovie.getTitle());
			eachMovieMap.put("genre", eachMovie.getGenre());
			eachMovieMap.put("year", String.valueOf(eachMovie.getYear()));
			eachMovieMap.put("studio", eachMovie.getStudio());
			eachMovieMap.put("synopsis", eachMovie.getSynopsis());
			eachMovieMap.put("imageUrl", eachMovie.getImageUrl());
			eachMovieMap.put("actors", eachMovie.getActors());
			eachMovieMap.put("director", eachMovie.getDirector());
			eachMovieMap.put("country", eachMovie.getCountry());
			eachMovieMap.put("rating", eachMovie.getRating());
			eachMovieMap.put("availability", eachMovie.getAvailability());
			eachMovieMap.put("price", String.valueOf(eachMovie.getPrice()));
			responseList.add(eachMovieMap);
		}

		return new ResponseEntity(responseList, null, status);
    }


	
	@RequestMapping(value = "/getAllMovies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMovies() {
		HttpStatus status = HttpStatus.OK;

		List<Map<String, String>> responseList = new ArrayList<>();		
		
		List<MoviesModel> allMovies = repository.getAllMovies();
		
		System.out.println(allMovies.size());
		for(MoviesModel eachMovie: allMovies) {
			Map<String, String> eachMovieMap = new HashMap<>();
			eachMovieMap.put("title", eachMovie.getTitle());
			eachMovieMap.put("genre", eachMovie.getGenre());
			eachMovieMap.put("year", String.valueOf(eachMovie.getYear()));
			eachMovieMap.put("studio", eachMovie.getStudio());
			eachMovieMap.put("synopsis", eachMovie.getSynopsis());
			eachMovieMap.put("imageUrl", eachMovie.getImageUrl());
			eachMovieMap.put("actors", eachMovie.getActors());
			eachMovieMap.put("director", eachMovie.getDirector());
			eachMovieMap.put("country", eachMovie.getCountry());
			eachMovieMap.put("rating", eachMovie.getRating());
			eachMovieMap.put("availability", eachMovie.getAvailability());
			eachMovieMap.put("price", String.valueOf(eachMovie.getPrice()));
			
			responseList.add(eachMovieMap);
		}
		
		return new ResponseEntity(responseList, null, status);
	}
	
	@RequestMapping(value = "/addMovie", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovieByAdmin(@RequestBody Map map, HttpSession session)
	{
        
		String title = (String) map.get("title");
		System.out.printf("here I am ", title);
		String genre = (String) map.get("genre");
		int year = (int) map.get("year");
		String studio = (String) map.get("studio");
		String synopsis = (String) map.get("synopsis");
		String image_url = (String) map.get("image_url");
		String actors = (String) map.get("actors");
		String director = (String) map.get("director");
		String country = (String) map.get("country");
		String rating =  (String) map.get("rating");
		String availability = (String) map.get("availability");
		int price = (int) map.get("price");
		String movie_url = (String) map.get("movie_url");
		
		return addMovie(title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movie_url);
		
		
}
	private ResponseEntity<?> addMovie(String title,String genre,int year,String studio,String synopsis,String image_url,String actors,String director,String country, String rating,String availability,int price, String movie_url)
	{
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		String data = "";
	try
	{
		    repository.addMovieAdmin(title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movie_url); 
            HttpHeaders httpHeaders = new HttpHeaders();			
            return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);      
	}
	catch(Exception e)
	
	{e.printStackTrace();}
	return responseEntity;
		
		/*
		ResponseEntity responseEntity = new ResponseEntity("server error", HttpStatus.NOT_FOUND);
		HttpHeaders httpHeaders = new HttpHeaders();
		try
		{
			
			repository.addMovieAdmin(title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movie_url); 
	        return new ResponseEntity<>("server_success", null, HttpStatus.OK);      
	        }
		
		catch(Exception e) {e.printStackTrace();}
		return responseEntity;*/

	}

	

}
