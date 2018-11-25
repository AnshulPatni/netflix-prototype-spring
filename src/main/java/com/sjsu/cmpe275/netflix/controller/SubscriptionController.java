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

import com.sjsu.cmpe275.netflix.model.SubscriptionModel;
import com.sjsu.cmpe275.netflix.repository.SubscriptionRepository;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {
	
	@Autowired
	SubscriptionRepository repository;

	@RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSubscriptionDetails(@PathVariable("email") String email) {
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
	@RequestMapping(value = "/checkBillingStatus/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkBillingStatus(@PathVariable("email") String email) {
		HttpStatus status = HttpStatus.OK;
		Map<String, String> responseMap = new HashMap<>();
		
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
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSubscriptionDetails(@RequestBody Map map, HttpSession session) {
		@SuppressWarnings("unchecked")
		HttpStatus status = HttpStatus.OK;
//		Subscription subscription = repository.getSubscriptionDetails(map.get("email").toString());
		int days = (int) map.get("days");
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("email", map.get("email").toString());
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String subscriptionStartDateString = "";
			String subscriptionEndDateString = "";
			if(repository.getSubscriptionStartDate(map.get("email").toString()) == null) {
				subscriptionStartDateString = df.format(java.time.LocalDate.now());
				Date subscriptionStartDate = Date.valueOf(java.time.LocalDate.now());
				repository.updateSubscriptionStartDate(map.get("email").toString(), subscriptionStartDate);
			} else {
				subscriptionStartDateString = df.format(repository.getSubscriptionStartDate(map.get("email").toString()));
			}
			
			Calendar c = Calendar.getInstance();
			
			if(repository.getSubscriptionEndDate(map.get("email").toString()) == null) {
				c.setTime(Date.valueOf(java.time.LocalDate.now()));
		        c.add(Calendar.DATE, days);
		        Date subscriptionEndDate = new Date(c.getTimeInMillis());
				repository.updateSubscriptionEndDate(map.get("email").toString(), subscriptionEndDate);
				subscriptionEndDateString = df.format(subscriptionEndDate);
			} else {
				c.setTime(Date.valueOf(repository.getSubscriptionEndDate(map.get("email").toString()).toString()));
		        c.add(Calendar.DATE, days);
		        Date subscriptionEndDate = new Date(c.getTimeInMillis());
				repository.updateSubscriptionEndDate(map.get("email").toString(), subscriptionEndDate);
				subscriptionEndDateString = df.format(subscriptionEndDate);
			}
			
			responseMap.put("subscriptionStartDate", subscriptionStartDateString);
			responseMap.put("subscriptionEndDate", subscriptionEndDateString);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(responseMap, null, status);
    }
//	
//	private ResponseEntity<?> updateSubscription(String email) {
//			
//			Subscription subscription = new Subscription();
//			
//			subscription.setEmail(email);
//			subscription.setSubscriptionStartDate(repository.getSubscriptionStartDate(email));
//			subscription.setSubscriptionEndDate(repository.getSubscriptionEndDate(email));
//	            
//	        return new ResponseEntity<>(subscription, HttpStatus.OK);
//
//    }


}
