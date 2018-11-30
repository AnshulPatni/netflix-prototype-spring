package com.sjsu.cmpe275.netflix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.repository.UserDetailsRepository;
import com.sjsu.cmpe275.netflix.model.UserDetailsModel;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/userDetails")
public class UserDetailsController {

	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserDetails(@PathVariable("email") String email) {
		
		HttpStatus status = HttpStatus.OK;
		Map<String, String> responseMap = new HashMap<>();
		
		UserDetailsModel userDetails = userDetailsRepository.getUserDetails(email);
		
		responseMap.put("email", userDetails.getEmail());
		responseMap.put("name", userDetails.getName());
		responseMap.put("contactNo", userDetails.getContactNo());
		responseMap.put("city", userDetails.getCity());
		responseMap.put("state", userDetails.getState());

        return new ResponseEntity<>(responseMap, null, HttpStatus.OK);
        
    }
	
}
