package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sjsu.cmpe275.netflix.repository.PayPerViewRepository;
import com.sjsu.cmpe275.netflix.repository.TransactionRepository;
import com.sjsu.cmpe275.netflix.repository.MoviesRepository;
import com.sjsu.cmpe275.netflix.repository.SubscriptionRepository;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/payPerView")
public class PayPerViewController {

	@Autowired
	PayPerViewRepository payPerViewRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	MoviesRepository moviesRepository;
	
	@Autowired
	SubscriptionRepository subscriptionRepository;



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/payForPayPerView", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertIntoPayPerView(@RequestBody Map map, HttpSession session) {
		
		HttpStatus status = HttpStatus.OK;
		Map<String, String> responseMap = new HashMap<>();
		
		try {
			
			String email = map.get("email").toString();
			String title = map.get("title").toString();
			
			payPerViewRepository.insertPayPerView(email, title, "subscribed");
			
			int amount = moviesRepository.getMoviePrice(title);
			
			Date subscriptionEndDate = subscriptionRepository.getSubscriptionEndDate(email);
			
			if(subscriptionEndDate != null && subscriptionEndDate.after(Date.valueOf(java.time.LocalDate.now()))) {
				amount /= 2;
			}
			
			Date currDate = Date.valueOf(LocalDate.now());
			
			transactionRepository.insertTransaction(email, amount, currDate);
			
			responseMap.put("email", email);
			responseMap.put("title", title);
			responseMap.put("amount", Integer.toString(amount));
			responseMap.put("message", "Successfully subscribed for pay per view.");
			
		} catch(Exception e) {
			status = HttpStatus.BAD_REQUEST;
			e.printStackTrace();
		}
		
		return new ResponseEntity(responseMap, null, status);
	
	}



	//FOR UNIQUE_PER_PER_VIEW_USER
//	@RequestMapping(value = "/uniquePayPerView/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> forUniquePayPerViewUser(@PathVariable("date") Date date) {
//		HttpStatus status = HttpStatus.OK;
//		List uniquePayPerViewList = payPerViewRepository.getUniquePayPerViewUser(date);
//		return new ResponseEntity(uniquePayPerViewList, null, status);
//	}

}
