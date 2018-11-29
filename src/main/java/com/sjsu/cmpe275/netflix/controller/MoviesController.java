package com.sjsu.cmpe275.netflix.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
		Map<String, Map<String, String>> responseMap = new HashMap<>();
		System.out.println(keyword);
		List<MoviesModel> allMovies = repository.getMoviesByKeyword(keyword);
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
			responseMap.put(eachMovie.getTitle(), eachMovieMap);
		}
		
		return new ResponseEntity(responseMap, null, status);
    }
	

}
