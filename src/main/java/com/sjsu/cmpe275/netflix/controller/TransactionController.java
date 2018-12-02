package com.sjsu.cmpe275.netflix.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.repository.TransactionRepository;
import com.sjsu.cmpe275.netflix.model.TransactionModel;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/transaction")
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;

	@RequestMapping(value = "/getAllTransactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransactions() {
		
		List<Map<String, String>>  responseList = new ArrayList<>();
		
		List<TransactionModel> transactions = transactionRepository.getTransactions();
		
		if(transactions.isEmpty()) {
			Map<String, String> tempMap = new HashMap<>();
			tempMap.put("message", "There are zero transactions.");
			responseList.add(tempMap);
		} else {
			for(TransactionModel transaction : transactions) {
				Map<String, String> tempMap = new HashMap<>();
				tempMap.put("transaction id", Integer.toString(transaction.getTransactionId()));
				tempMap.put("email", transaction.getEmail());
				tempMap.put("amount", Integer.toString(transaction.getAmount()));
				tempMap.put("date", transaction.getDate().toString());
				tempMap.put("transaction type", transaction.getTransactionType());
				responseList.add(tempMap);
			}
			return new ResponseEntity<>(responseList, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);
    }
	
}
