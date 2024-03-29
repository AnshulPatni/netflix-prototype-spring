package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.model.SubscriptionModel;
import com.sjsu.cmpe275.netflix.repository.SubscriptionRepository;
import com.sjsu.cmpe275.netflix.repository.TransactionRepository;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/subscription")
public class SubscriptionController {
	
	@Autowired
	SubscriptionRepository repository;
	
	@Autowired
	TransactionRepository transactionRepository;

	@RequestMapping(value = "/getSubscriptionDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSubscriptionDetails(HttpSession session) {
		String email = (String) session.getAttribute("userEmail");
        return getSubscription(email);
    }
	
	private ResponseEntity<?> getSubscription(String email) {

		SubscriptionModel subscription = new SubscriptionModel();
		subscription.setEmail(email);
		subscription.setSubscriptionStartDate(repository.getSubscriptionStartDate(email));
		subscription.setSubscriptionEndDate(repository.getSubscriptionEndDate(email));
            
        return new ResponseEntity<>(subscription, HttpStatus.OK);

    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkSubscriptionStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkSubscriptionStatus(HttpSession session) {
		HttpStatus status = HttpStatus.OK;
		Map<String, String> responseMap = new HashMap<>();
		String email = (String) session.getAttribute("userEmail");
		
		responseMap.put("email", email);
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String subscriptionStartDateString = "";
		String subscriptionEndDateString = "";
		if(repository.getSubscriptionStartDate(email) == null) {
			subscriptionStartDateString = null;
		} else {
			subscriptionStartDateString = df.format(repository.getSubscriptionStartDate(email));
		}
		
		if(repository.getSubscriptionEndDate(email) == null) {
			subscriptionEndDateString = null;
		} else {
			subscriptionEndDateString = df.format(repository.getSubscriptionEndDate(email));
		}
		
		responseMap.put("subscriptionStartDate", subscriptionStartDateString);
		responseMap.put("subscriptionEndDate", subscriptionEndDateString);
		
		if(repository.getSubscriptionEndDate(email) == null) {
			responseMap.put("status", "You have not subscribed to the services.");
		} else {
			String message = "Your subscription ends on " + repository.getSubscriptionEndDate(email);
			responseMap.put("status", message);
		}

		return new ResponseEntity(responseMap, null, status);
    }
	
	@RequestMapping(value = "/payForSubscription", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSubscriptionDetails(@RequestBody Map map, HttpSession session) {
		@SuppressWarnings("unchecked")
		HttpStatus status = HttpStatus.OK;

		int month = (int) map.get("month");
		Map<String, String> responseMap = new HashMap<>();
		String email = (String) session.getAttribute("userEmail");
		int days = month * 30;
		int amount = month * 10;
		
		responseMap.put("email", email);
		
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String subscriptionStartDateString = "";
			String subscriptionEndDateString = "";
			
			if(repository.getSubscriptionStartDate(email) == null) {
				subscriptionStartDateString = java.time.LocalDate.now().toString();
				Date subscriptionStartDate = Date.valueOf(java.time.LocalDate.now());
				Calendar c = Calendar.getInstance();
				c.setTime(subscriptionStartDate);
		        c.add(Calendar.DATE, days);
		        Date subscriptionEndDate = new Date(c.getTimeInMillis());
				subscriptionEndDateString = df.format(subscriptionEndDate);
				
				repository.insertSubscriptionDetails(email, subscriptionStartDate, subscriptionEndDate);
				
			} else {
				subscriptionStartDateString = df.format(repository.getSubscriptionStartDate(email));
				Calendar c = Calendar.getInstance();
				
				if(repository.getSubscriptionEndDate(email) == null) {
					c.setTime(Date.valueOf(java.time.LocalDate.now()));
			        c.add(Calendar.DATE, days);
			        Date subscriptionEndDate = new Date(c.getTimeInMillis());
					repository.updateSubscriptionEndDate(email, subscriptionEndDate);
					subscriptionEndDateString = df.format(subscriptionEndDate);
				} else {
					c.setTime(Date.valueOf(repository.getSubscriptionEndDate(email).toString()));
			        c.add(Calendar.DATE, days);
			        Date subscriptionEndDate = new Date(c.getTimeInMillis());
					repository.updateSubscriptionEndDate(email, subscriptionEndDate);
					subscriptionEndDateString = df.format(subscriptionEndDate);
				}
				
			}	
			
			responseMap.put("subscriptionStartDate", subscriptionStartDateString);
			responseMap.put("subscriptionEndDate", subscriptionEndDateString);
			
			Date currDate = Date.valueOf(LocalDate.now());
			
			transactionRepository.insertTransaction(email, amount, currDate, "Subscription");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(responseMap, null, status);
    }





	//API 6(a)-(i)
	//FOR UNIQUE_SUBSCRIPTION_USER
	@RequestMapping(value = "/uniqueSubscription/{year}/{month}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forUniqueSubscriptionUser(@PathVariable("year") int year, @PathVariable("month") int month) {
		HttpStatus status = HttpStatus.OK;
		String startDateString = "" + year + "-" + month + "-01";
		if(month == 12){
			year += 1;
			month = 1;
		}
		else {
			month = month + 1;
		}
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
		int uniqueSubscriberList = repository.getUniqueSubscriptionUser(startDate, endDate);
		return new ResponseEntity(uniqueSubscriberList, null, status);
	}





}
