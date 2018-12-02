package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
			String existingStatus = payPerViewRepository.getPayPerViewStatus(email);
			
			if(existingStatus == null) {
				payPerViewRepository.insertPayPerView(email, title, "subscribed");
			} else if(existingStatus.equals("unsubscribed")){
				payPerViewRepository.updatePayPerViewStatus(email, "subscribed");
			} else if(existingStatus.equals("subscribed")) {
				responseMap.put("email", email);
				responseMap.put("title", title);
				responseMap.put("amount", "0");
				responseMap.put("message", "You have already subscribed as pay per view for this movie.");
				return new ResponseEntity(responseMap, null, status);
			}
			
			
			int amount = moviesRepository.getMoviePrice(title);
			
			Date subscriptionEndDate = subscriptionRepository.getSubscriptionEndDate(email);
			
			if(subscriptionEndDate != null && subscriptionEndDate.after(Date.valueOf(java.time.LocalDate.now()))) {
				amount /= 2;
			}
			
			Date currDate = Date.valueOf(LocalDate.now());
			
			transactionRepository.insertTransaction(email, amount, currDate, "PayPerView");
			
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


	//API 6(a)-(i)
	//FOR UNIQUE_SUBSCRIPTION_USER
	@RequestMapping(value = "/uniquePayPerViewUser/{year}/{month}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forUniquePayPerViewUser(@PathVariable("year") int year, @PathVariable("month") int month) {
		HttpStatus status = HttpStatus.OK;
		String startDateString = "" + year + "-" + month + "-01";
		month = month + 1;
		String endDateString = "" + year + "-" + month + "-01";
		System.out.println(startDateString);
		System.out.println(endDateString);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date startDate=Date.valueOf(startDateString);
		Date endDate=Date.valueOf(endDateString);
//		Date startDate = format.parse("startDateString");
//		Date endDate = format.parse("startDateString");
//
//		java.sql.Date sql = new java.sql.Date(parsed.getTime());

		System.out.println(startDate);
		System.out.println(endDate);
		int uniquePayPerViewUser = payPerViewRepository.getUniquePayPerViewUser(startDate, endDate);
		return new ResponseEntity(uniquePayPerViewUser, null, status);
	}

}
