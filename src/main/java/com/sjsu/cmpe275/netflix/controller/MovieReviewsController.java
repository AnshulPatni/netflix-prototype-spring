package com.sjsu.cmpe275.netflix.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.cmpe275.netflix.repository.MovieReviewsRepository;
import com.sjsu.cmpe275.netflix.repository.MoviesRepository;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/movies")
public class MovieReviewsController {

	@Autowired
	MovieReviewsRepository repository;
	
	@Autowired
	MoviesRepository moviesRepository;

	@RequestMapping(value = "/insertReview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertMovieReview(@RequestBody Map map, HttpSession session) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		Map<String, String> responseMap = new HashMap<>();
		
		try {	
			String title = map.get("title").toString();
			responseMap.put("title", title);
			int reviewRating = (int) map.get("reviewRating");
			responseMap.put("review_rating", Integer.toString(reviewRating));
			String review = map.get("review").toString();
			responseMap.put("review", review);
			repository.insertMovieReview(title, reviewRating, review);
			
			float avgStars = moviesRepository.getAvgStars(title);
			int noOfReviews = moviesRepository.getNoOfReviews(title);
						
			avgStars = ((avgStars * (float) noOfReviews) + (float) reviewRating) / ((float) (noOfReviews + 1));
			
			moviesRepository.updateAvgStarsAndReviewCount(title, avgStars, noOfReviews+1);
			status = HttpStatus.OK;
			return new ResponseEntity(responseMap, null, status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(responseMap, null, status);
	}

	
	
	
	
	@RequestMapping(value = "/scorecard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHitory() {
        return getScorecard();
    }
	
	private ResponseEntity<?> getScorecard() {
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		HttpHeaders httpHeaders = new HttpHeaders();
		try
		{
			List questionOptional = repository.getScoreCardByStar(); 
			for(Object i: questionOptional)
			{
				
			}
			System.out.printf("I ma here", questionOptional);
			if(questionOptional.isEmpty())
	        {
	        	
	        	return new ResponseEntity<>( "No Data found in this TimeFrame", HttpStatus.NOT_FOUND);
	        	
	        	
	        } 
	        else 
	        {
	            
	            Map<String, Object> json = new HashMap<String, Object>();
		        json.put("status_code", 200);
		        json.put(" Movies in Reverse chronological order for this user: ", questionOptional);
		        String data="";
		        try 
		           {
			         ObjectMapper mapper = new ObjectMapper();
			         data  = mapper.writeValueAsString(json);
		            }
	            catch(Exception e) {}
	            return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);      
	        }
		}
		catch(Exception e) {e.printStackTrace();}
		return responseEntity;
	}
}
