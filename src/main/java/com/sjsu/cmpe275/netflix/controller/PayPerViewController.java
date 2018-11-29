package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.repository.PayPerViewRepository;
import com.sjsu.cmpe275.netflix.repository.TransactionRepository;

@RestController
@RequestMapping(value = "/payPerView")
public class PayPerViewController {

	@Autowired
	PayPerViewRepository payPerViewRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/payForPayPerView", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertIntoPayPerView(@RequestBody Map map, HttpSession session) {
		
		HttpStatus status = HttpStatus.OK;
		Map<String, String> responseMap = new HashMap<>();
		
		String email = map.get("email").toString();
		String title = map.get("title").toString();
		String amount = map.get("amount").toString();
		
		responseMap.put("email", email);
		responseMap.put("title", title);
		responseMap.put("amount", amount);
		responseMap.put("message", "Successfully");
		
		payPerViewRepository.insertPayPerView(email, title, "subscribed");
		
		int amountInInt = Integer.valueOf(amount);
		
		Date currDate = Date.valueOf(LocalDate.now());
		
		transactionRepository.insertTransaction(email, amountInInt, currDate);
		
		return new ResponseEntity(responseMap, null, status);
	
	}

}
