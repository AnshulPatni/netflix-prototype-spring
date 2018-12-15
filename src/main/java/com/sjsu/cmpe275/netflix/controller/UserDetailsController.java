package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sjsu.cmpe275.netflix.repository.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sjsu.cmpe275.netflix.repository.UserDetailsRepository;
import com.sjsu.cmpe275.netflix.model.UserDetailsModel;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/userDetails")
public class UserDetailsController {

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	UserActivityRepository userActivityRepository;
	
	@RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserDetails(@PathVariable("email") String email) {
		
		HttpStatus status = HttpStatus.OK;
		Map<String, String> responseMap = new HashMap<>();
		
		UserDetailsModel userDetails = userDetailsRepository.getUserDetails(email);
		
		if(userDetails != null) {
			responseMap.put("email", userDetails.getEmail());
			responseMap.put("name", userDetails.getName());
			responseMap.put("contactNo", userDetails.getContactNo());
			responseMap.put("city", userDetails.getCity());

	        return new ResponseEntity<>(responseMap, null, HttpStatus.OK);
		} else {
			responseMap.put("message", "No such user exists.");
			return new ResponseEntity<>(responseMap, null, HttpStatus.NOT_FOUND);
		}
    }


	//	FOR UNIQUE_ACTIVE_USER
	@RequestMapping(value = "/totalUniqueActiveUser/{year}/{month}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forTotalUniqueActiveUser(@PathVariable("year") int year, @PathVariable("month") int month) {
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
		int totalUniqueActiveUserList = userDetailsRepository.getTotalActiveUniqueUser(startDate, endDate);
		return new ResponseEntity(totalUniqueActiveUserList, null, status);
	}

	//	FOR TOTAL_UNIQUE_USER
	@RequestMapping(value = "/totalUniqueUser/{year}/{month}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forTotalUniqueUser(@PathVariable("year") int year, @PathVariable("month") int month) {
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
		int uniqueActiveUserList = userDetailsRepository.getTotalUniqueUser(startDate, endDate);
		return new ResponseEntity(uniqueActiveUserList, null, status);
	}
	
}
