package com.sjsu.cmpe275.netflix.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.repository.TransactionRepository;
import com.sjsu.cmpe275.netflix.model.TransactionModel;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;

	@RequestMapping(value = "/getAllTransactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransactions() {
		Map<String, Map<String, String>> responseMap = new HashMap<>();
		
		List<TransactionModel> transactions = transactionRepository.getTransactions();
		
		if(transactions.isEmpty()) {
			Map<String, String> tempMap = new HashMap<>();
			tempMap.put("message", "There are zero transactions.");
			responseMap.put("Error", tempMap);
		} else {
			for(TransactionModel transaction : transactions) {
				Map<String, String> tempMap = new HashMap<>();
				tempMap.put("email", transaction.getEmail());
				tempMap.put("amount", Integer.toString(transaction.getAmount()));
				tempMap.put("date", transaction.getDate().toString());
				responseMap.put(Integer.toString(transaction.getTransactionId()), tempMap);
			}
		}
		
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
	
}
