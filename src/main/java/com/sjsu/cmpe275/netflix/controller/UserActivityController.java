package com.sjsu.cmpe275.netflix.controller;


import com.sjsu.cmpe275.netflix.model.MoviesModel;
import com.sjsu.cmpe275.netflix.repository.MoviesRepository;
import com.sjsu.cmpe275.netflix.repository.SubscriptionRepository;
import org.json.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.cmpe275.netflix.model.MoviesModel;
import com.sjsu.cmpe275.netflix.model.UserActivityModel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.SqlResultSetMapping;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

import com.sjsu.cmpe275.netflix.repository.UserActivityRepository;

import javassist.tools.web.BadHttpRequest;
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/user")
public class UserActivityController {

	private org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserActivityRepository repository;
	
	
	
// get particular data based on title of movie
// API 8: to get custmomer details based on title of movie
	@RequestMapping(value = "/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDatabyTitle(@PathVariable("title") String title) {
        return getInformation(title);
    }
	private ResponseEntity<?> getInformation(String title) {
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		String data = "";
	try
	{
        List questionOptional = repository.findAllActiveUsers(title); 
 
        if(questionOptional.isEmpty())
        {
        	
        	return new ResponseEntity<>( "No Data found for this Title", HttpStatus.NOT_FOUND);
        	
        	
        } 
        else 
        {        
            HttpHeaders httpHeaders = new HttpHeaders();
            List<HashMap<String,String>> lst = new ArrayList<HashMap<String,String>>();
			for (Object i: questionOptional)
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("Title", i.toString());
				lst.add((HashMap<String, String>) map);
				//System.out.printf("I am here",i);
				
			}
            return new ResponseEntity<>(lst, httpHeaders, HttpStatus.OK);      
        }
	}
	catch(Exception e)
	
	{e.printStackTrace();}
	return responseEntity;
}
	
	
	
//API getnumber 10:5a of plays: input: moviename, period
	// @SuppressWarnings(value = { "" })
	@RequestMapping(value = "/{title}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDatabymovienamedate(@PathVariable("title") String title, @PathVariable("date") String date) {
		 
		return getInformationbynamedate(date, title);
    }
	private ResponseEntity<?> getInformationbynamedate(String time, String title) {
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		LocalDate date;
		try
		{
			        if(time.equals("day")){date = LocalDate.now().minusDays(1); }
			        else if(time.equals("week"))  {date = LocalDate.now().minusDays(7); }
			        else if (time.equals("month")){ date = LocalDate.now().minusDays(30); }
			        else {return new ResponseEntity<>( "Please choose between 24 hours, 1 week or 1 month", HttpStatus.NOT_FOUND);}
				    String data = "";
				    Date new_date = java.sql.Date.valueOf(date);
				    System.out.printf("date is", new_date);
		            int questionOptional = repository.getDatabyNameAndPeriod(new_date, title); 
		            //System.out.printf("Value of date", questionOptional);
		            if(questionOptional != 0)
		            {
					        HttpHeaders httpHeaders = new HttpHeaders();
							Map<String, Integer> map = new HashMap<String, Integer>();
							map.put("NoOfPlay", questionOptional);
						    //System.out.printf("I am here",i);
								
							
				      return new ResponseEntity(map ,httpHeaders, HttpStatus.OK);   
		            }
		            else
		            {
		            	return new ResponseEntity<>( "No Data found for this Title within given period", HttpStatus.NOT_FOUND);
		            }
			       
		}
		catch(Exception e) {e.printStackTrace();}
		return responseEntity;
}
	
	
//API 4C: An admin must be able to view the top ten users who have the most # of movie plays 
	/*in a given period, which can be last 24 hours, last week, and last month. For every movie, 
	it can be counted as only one play for the same customer within 24 hours. 
	Please note this rule applies to elsewhere too when counting movie plays.
*/
	
	
	

//API 4c: to get top 10 users
	
	@RequestMapping(value = "/topTenUser/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTopTen(@PathVariable("date") String date) {
        return getInformationbydate(date);
    }
	private ResponseEntity<?> getInformationbydate(String time) {
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		LocalDate date;
		
		try
		{
		
			 if(time.equals("day")){date = LocalDate.now().minusDays(1); }
		     else if(time.equals("week"))  {date = LocalDate.now().minusDays(7); }
		     else if (time.equals("month")){ date = LocalDate.now().minusDays(30); }
		     else {return new ResponseEntity<>( "Please choose between 24 hours, 1 week or 1 month", HttpStatus.NOT_FOUND);}
			 HttpHeaders httpHeaders = new HttpHeaders();
			 System.out.printf("date is",date);

			 Date new_date = java.sql.Date.valueOf(date);
			
	         List questionOptional = repository.getTopTenUsers(new_date); 

	         if(questionOptional.isEmpty())
	         {
	        	
	        	return new ResponseEntity<>( "No Data found in this TimeFrame", HttpStatus.NOT_FOUND);
	        	
	        	
	         } 
	         else 
	         {
	            
	        	 List<HashMap<String,String>> lst = new ArrayList<HashMap<String,String>>();
					for (Object i: questionOptional)
					{
						Map<String, String> map = new HashMap<String, String>();
						map.put("User", i.toString());
						lst.add((HashMap<String, String>) map);
						//System.out.printf("I am here",i);
						
					}
	            return new ResponseEntity<>(lst, httpHeaders, HttpStatus.OK);      
	         }
		}
		catch(Exception e) {e.printStackTrace();}
		return responseEntity;
 }	
	
	
	
	
//API 11:5b get top ten movies based on no of plays in a given period of time
	@RequestMapping(value = "/topTenMovies/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTopTenMoviesbyDate(@PathVariable("date") String date) {
        return getTopTenMovieInformationbydate(date);
    }
	private ResponseEntity<?> getTopTenMovieInformationbydate(String time) {
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		LocalDate date;
		HttpHeaders httpHeaders = new HttpHeaders();
		try
		{
		
			 if(time.equals("day")){date = LocalDate.now().minusDays(1); }
		     else if(time.equals("week"))  {date = LocalDate.now().minusDays(7); }
		     else if (time.equals("month")){ date = LocalDate.now().minusDays(30); }
		     else {return new ResponseEntity<>( "Please choose between 24 hours, 1 week or 1 month", HttpStatus.NOT_FOUND);}
			 
			 Date new_date = java.sql.Date.valueOf(date);
		     List questionOptional = repository.getTopTenMovies(new_date); 
		     if(questionOptional.isEmpty())
		        {
		        	
		        	return new ResponseEntity<>( "No Data found in this TimeFrame", HttpStatus.NOT_FOUND);
		        	
		        	
		        } 
		        else 
		        {
		        	List<HashMap<String,String>> lst = new ArrayList<HashMap<String,String>>();
					for (Object i: questionOptional)
					{
						Map<String, String> map = new HashMap<String, String>();
						map.put("moviename", i.toString());
						lst.add((HashMap<String, String>) map);
						//System.out.printf("I am here",i);
						
					}
		            return new ResponseEntity<>(lst, httpHeaders, HttpStatus.OK);      
		        }
		}
	catch(Exception e) {e.printStackTrace();}
		return responseEntity;
	}
	
	
	
//API 4b: An admin must be able to view the movie playing history (in reverse chronological order) of any customer.
	@RequestMapping(value = "/movieHistorys/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHitory(@PathVariable("userName") String userName) {
        return getMovieHistory(userName);
    }
	
	private ResponseEntity<?> getMovieHistory(String userName) {
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		HttpHeaders httpHeaders = new HttpHeaders();
		try
		{
			List questionOptional = repository.getMoviesHistoryByUser(userName); 
			if(questionOptional.isEmpty())
	        {
	        	
	        	return new ResponseEntity<>( "No Data found in this TimeFrame", HttpStatus.NOT_FOUND);
	        	
	        	
	        } 
	        else 
	        {
	            
	        	List<HashMap<String,String>> lst = new ArrayList<HashMap<String,String>>();
				for (Object i: questionOptional)
				{
					Map<String, String> map = new HashMap<String, String>();
					map.put("moviename", i.toString());
					lst.add((HashMap<String, String>) map);
					//System.out.printf("I am here",i);
					
				}
	            return new ResponseEntity<>(lst, httpHeaders, HttpStatus.OK);      
	        }
		}
		catch(Exception e) {e.printStackTrace();}
		return responseEntity;


	}


//	@RequestMapping(value = "/uniqueActiveUser/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> forUniqueActiveUser(@PathVariable("userName") String userName) {
//		HttpStatus status = HttpStatus.OK;
//		List uniqueSubscriberList = repository.getUniqueActiveUser(userName);
//		return new ResponseEntity(uniqueSubscriberList, null, status);
//	}
//
//	@RequestMapping(value = "/totalUniqueUser/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> forUniqueActiveUser(@PathVariable("userName") String userName) {
//		HttpStatus status = HttpStatus.OK;
//		List uniqueSubscriberList = repository.getUniqueActiveUser(userName);
//		return new ResponseEntity(uniqueSubscriberList, null, status);
//	}

	
	@RequestMapping(value = "/userHistory/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserHistory(@PathVariable("email") String email) {
		
		HttpStatus status = HttpStatus.OK;

		List<Map<String, String>> responseList = new ArrayList<>();		
		
		List<String> userHistory = repository.getUserHistory(email);
		
		System.out.println(userHistory.size());
		for(String eachMovie: userHistory) {
			Map<String, String> eachMovieMap = new HashMap<>();
			eachMovieMap.put("name", eachMovie);			
			responseList.add(eachMovieMap);
		}
		
		return new ResponseEntity(responseList, null, status);
    }
	
	
	
}	
	
	
	

