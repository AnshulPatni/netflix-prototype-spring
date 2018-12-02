package com.sjsu.cmpe275.netflix.controller;

import java.sql.Date;
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
		
		responseMap.put("email", userDetails.getEmail());
		responseMap.put("name", userDetails.getName());
		responseMap.put("contactNo", userDetails.getContactNo());
		responseMap.put("city", userDetails.getCity());
		responseMap.put("state", userDetails.getState());

        return new ResponseEntity<>(responseMap, null, HttpStatus.OK);
        
    }


	//	FOR UNIQUE_ACTIVE_USER
	@RequestMapping(value = "/totalUniqueActiveUser/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forTotalUniqueActiveUser(@PathVariable("date") int date) {
		HttpStatus status = HttpStatus.OK;
		int totalUniqueActiveUserList = userDetailsRepository.getTotalActiveUniqueUser(date);
		return new ResponseEntity(totalUniqueActiveUserList, null, status);
	}

	//	FOR TOTAL_UNIQUE_USER
	@RequestMapping(value = "/totalUniqueUser/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forTotalUniqueUser(@PathVariable("date") int date) {
		HttpStatus status = HttpStatus.OK;
		int uniqueActiveUserList = userDetailsRepository.getTotalUniqueUser(date);
		return new ResponseEntity(uniqueActiveUserList, null, status);
	}













//	//REGISTER
//	@PostMapping(value = "/register", produces = "application/json")
//	private ResponseEntity<?> registerUser(@RequestParam(value = "username") String username,
//										   @RequestParam(value = "password") String password,
//										   @RequestParam(value = "email") String email,
//										   @RequestParam(value = "age") String age) {
//		Optional<UserDetailsModel> userOptional = userDetailsRepository.findByEmail(email);
//		if (!userOptional.isPresent()) {
//			// String userId = UUID.nameUUIDFromBytes(email.getBytes()).toString();
//			String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
//			String activationCode = String.valueOf(new Random(System.nanoTime()).nextInt(100000));
//			UserDetailsModel user = new User(username, encodedPassword, email, age, false, activationCode);
//			String encodedEmail = Base64.getEncoder().encodeToString(email.getBytes());
//
//			// sending verification email
//			userDetailsRepository.save(user);
//			String text = "Your verification code is " + activationCode + "\n";
//			emailService.sendInvitationForUser(email, "Verification email for surveyApe", text);
//
//			return new ResponseEntity<>(user, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(new BadRequest(400, "Email already registered"), HttpStatus.BAD_REQUEST);
//		}
//	}
	
}
