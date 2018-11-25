package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.repository.MovieReviewsRepository;

@RestController
@RequestMapping(value = "/movies")
public class MovieReviewsController {

	@Autowired
	MovieReviewsRepository repository;

	@RequestMapping(value = "/insertReview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertMovieReview(@RequestBody Map map, HttpSession session) {
		HttpStatus status = HttpStatus.OK;
//		Subscription subscription = repository.getSubscriptionDetails(map.get("email").toString());
		Map<String, String> responseMap = new HashMap<>();
		
		try {	
			String title = map.get("title").toString();
			responseMap.put("title", title);
			int reviewRating = (int) map.get("reviewRating");
			responseMap.put("review_rating", Integer.toString(reviewRating));
			String review = map.get("review").toString();
			responseMap.put("review", review);
			repository.insertMovieReview(title, reviewRating, review);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(responseMap, null, status);
	}
	
}
