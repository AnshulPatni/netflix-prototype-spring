package com.sjsu.cmpe275.netflix.controller;

import com.sjsu.cmpe275.netflix.model.MoviesModel;
import com.sjsu.cmpe275.netflix.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/report")
public class FinancialReportController {

	//add_movie	edit_movie,	delete_movie,	search_movie,	review_movie,	get_top_movie_on_stars
	//Movie,	Movie_review
//
//	@Autowired
//	MoviesRepository repository;
//
//	@RequestMapping(value = "/{month}/{year}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> monthlyIncome(@PathVariable("month") String keyword) {
//		HttpStatus status = HttpStatus.OK;
//		}
//
//		return new ResponseEntity(responseMap, null, status);
//	}
	

}
