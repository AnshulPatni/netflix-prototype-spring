package com.sjsu.cmpe275.netflix.controller;

import com.sjsu.cmpe275.netflix.model.MoviesModel;
import com.sjsu.cmpe275.netflix.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/movies")
public class SearchFilterController {



    @Autowired
    MoviesRepository repository;

    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterMoviesRoute(@RequestParam Map<String, String> parameters) {
        HttpStatus status = HttpStatus.OK;
        List<Map<String, String>> responseList = new ArrayList<>();
        System.out.println(parameters);
        String genre = parameters.get("genre");
        int year = Integer.parseInt(parameters.get("year"));
        String actors = parameters.get("actors");
        String director = parameters.get("director");
        String rating = parameters.get("rating");
        float avgStars = Float.valueOf(parameters.get("avgStars"));
        System.out.println(year);
        System.out.println(avgStars);
        System.out.println(genre);
        System.out.println(director);

        List<Map<String, String>> responseList2 = new ArrayList<>();
        List<MoviesModel> allMovies = repository.filterBY(genre, year, actors, director, rating, avgStars);

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
            eachMovieMap.put("avgStars", String.valueOf(eachMovie.getAvgStars()));
            responseList2.add(eachMovieMap);
        }

        return new ResponseEntity(responseList2, null, status);

    }


}
	


