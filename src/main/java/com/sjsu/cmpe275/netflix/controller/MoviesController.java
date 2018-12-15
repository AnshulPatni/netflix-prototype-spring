package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
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
import com.sjsu.cmpe275.netflix.repository.SubscriptionRepository;
import com.sjsu.cmpe275.netflix.repository.PayPerViewRepository;
import com.sjsu.cmpe275.netflix.repository.RecommendationRepository;
import com.sjsu.cmpe275.netflix.model.MoviesModel;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/movies")
public class MoviesController {

	//add_movie	edit_movie,	delete_movie,	search_movie,	review_movie,	get_top_movie_on_stars
	//Movie,	Movie_review

	@Autowired
	MoviesRepository repository;
	
	@Autowired
	RecommendationRepository recommendationRepository;
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Autowired
	PayPerViewRepository payPerViewRepository;

	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchMoviesByKeyword(@PathVariable("keyword") String keyword, HttpSession session) {
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
			eachMovieMap.put("movieUrl", eachMovie.getMovieUrl());
			eachMovieMap.put("avgStars", String.valueOf(eachMovie.getAvgStars()));
			responseList.add(eachMovieMap);
			
		}
	
		MoviesModel lt = allMovies.get(0);
		System.out.println(lt.getActors());
		System.out.println(session.getAttribute("userEmail").toString());
		String name = recommendationRepository.getAlreadyuser(session.getAttribute("userEmail").toString());
		if(name == null) {
			recommendationRepository.recommendationMoviesByKeyword(session.getAttribute("userEmail").toString(), keyword ,lt.getActors(),lt.getGenre(),lt.getDirector());
		}
		else {
			recommendationRepository.recommendationMoviesByKeywordUpdate(session.getAttribute("userEmail").toString(), keyword ,lt.getActors(),lt.getGenre(),lt.getDirector());

		}

		return new ResponseEntity(responseList, null, status);
    }
	
	
	@RequestMapping(value = "/recommendation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> recommendation(HttpSession session) {
		HttpStatus status = HttpStatus.OK;
		
		String email = (String) session.getAttribute("userEmail");
		
		Map<String, String> responseMap = new HashMap<>();
		List lst = new ArrayList();
		try {
			
			List<Object[]> allMoviesRecommended = recommendationRepository.getDatarecommendedMovies(email);
			System.out.println(allMoviesRecommended.size());
			if(allMoviesRecommended.size() != 0) {
				for(Object[] i: allMoviesRecommended)
				{
					lst.add((String) i[0]);
					lst.add((String) i[1]);
					lst.add((String) i[2]);
					lst.add((String) i[3]);
				}
	            System.out.println(lst);
				
	            List<String> recommendMovies = repository.recommendMovies(lst.get(0).toString(),lst.get(1).toString(),lst.get(2).toString());
				
				List<HashMap<String,String>> new_lst = new ArrayList<HashMap<String,String>>();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("keyword", lst.get(3).toString());
				HashMap<String, HashMap<String, String>> movieMap = new HashMap<>();
				
				for (String i: recommendMovies) {
					HashMap<String, String> tempMap = new HashMap<>();
					tempMap.put("keyword", lst.get(3).toString());
					tempMap.put("Movie", i.toString());
					new_lst.add(tempMap);
				}
				return new ResponseEntity(new_lst, null, status);
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(responseMap, null, status);
    }
	
	
	@RequestMapping(value = "/findPrice/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findPrice(@PathVariable("title") String title, HttpSession session) {
		HttpStatus status = HttpStatus.OK;
		
		String email = (String) session.getAttribute("userEmail");
		
		Map<String, String> responseMap = new HashMap<>();
		
		try {
			responseMap.put("title", title);
			
			int amount = repository.getMoviePrice(title);
			
			String movieType = repository.getMovieType(title);

			Date subscriptionEndDate = subscriptionRepository.getSubscriptionEndDate(email);
			
			String payPerViewStatus = payPerViewRepository.getPayPerViewStatus(email);
			
			if(movieType.equals("PayPerView")) {
				if(payPerViewStatus != null && payPerViewStatus.equals("subscribed")) {
					amount = 0;
				} else if(subscriptionEndDate != null && subscriptionEndDate.after(Date.valueOf(java.time.LocalDate.now()))) {
				amount = amount / 2;
				}
			} else if(movieType.equals("SubscriptionOnly")) {
				if(subscriptionEndDate != null && subscriptionEndDate.after(Date.valueOf(java.time.LocalDate.now()))) {
					amount = 0;
				} else {
				amount = 10;
				}
			} else if(movieType.equals("Free")) {
				amount = 0;
			} else if(movieType.equals("Paid")) {
				if(subscriptionEndDate != null && subscriptionEndDate.after(Date.valueOf(java.time.LocalDate.now()))) {
					amount = 0;
				} else if(payPerViewStatus != null && payPerViewStatus.equals("subscribed")) {
					amount = 0;
				}
			}
			
			responseMap.put("amount",String.valueOf(amount));
									
			return new ResponseEntity(responseMap, null, status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(responseMap, null, status);
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
			eachMovieMap.put("movieUrl", eachMovie.getMovieUrl());
			eachMovieMap.put("avgStars", String.valueOf(eachMovie.getAvgStars()));
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
		String imageUrl = (String) map.get("image_url");
		String actors = (String) map.get("actors");
		String director = (String) map.get("director");
		String country = (String) map.get("country");
		String rating =  (String) map.get("rating");
		String availability = (String) map.get("availability");
		int price = (int) map.get("price");
		String movieUrl = (String) map.get("movie_url");
		
		return addMovie(title, genre, year, studio, synopsis, imageUrl, actors, director, country, rating, availability, price, movieUrl);
		
		
}
	private ResponseEntity<?> addMovie(String title,String genre,int year,String studio,String synopsis,String image_url,String actors,String director,String country, String rating,String availability,int price, String movie_url)
	{
		Map<String, String> responseMap = new HashMap<>();
		String data = "";
		try	{
			    repository.addMovieAdmin(title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movie_url);
			    responseMap.put("message", "The movie has been added successfully!");
	            return new ResponseEntity<>(responseMap, null, HttpStatus.OK);      
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		responseMap.put("message", "The movie could not be added.");
		return new ResponseEntity<>(responseMap, null, HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "/editMovie", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editMovieByAdmin(@RequestBody Map map, HttpSession session)
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
		String movieUrl = (String) map.get("movie_url");
		
		return editMovie(title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movieUrl);
		
		
}
	private ResponseEntity<?> editMovie(String title,String genre,int year,String studio,String synopsis,String image_url,String actors,String director,String country, String rating,String availability,int price, String movieUrl)
	{
		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
		String data = "";
	try
	{
		    repository.editMovieAdmin(title, genre, year, studio, synopsis, image_url, actors, director, country, rating, availability, price, movieUrl); 
            HttpHeaders httpHeaders = new HttpHeaders();			
            return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);      
	}
	catch(Exception e)
	
	{e.printStackTrace();}
	return responseEntity;

	}
	
	
	@RequestMapping(value = "/delMovie/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteMovie(@PathVariable("title") String title)
	{
		return delMovie(title);
	}
    
	private ResponseEntity<?> delMovie(String title)
	{
		ResponseEntity responseEntity = new ResponseEntity(null,null,HttpStatus.NOT_FOUND);
		String data = "";
	try
	{
		    repository.delMovieByAdmin(title); 
            HttpHeaders httpHeaders = new HttpHeaders();			
            return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);      
	}
	catch(Exception e)
	
	{e.printStackTrace();}
	return responseEntity;

	}
	
	
}
