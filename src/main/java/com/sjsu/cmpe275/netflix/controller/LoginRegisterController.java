package com.sjsu.cmpe275.netflix.controller;

import com.sjsu.cmpe275.netflix.model.BadRequest;
import com.sjsu.cmpe275.netflix.model.UserDetailsModel;
import com.sjsu.cmpe275.netflix.repository.UserDetailsRepository;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.support.SessionAttributeStore;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/user")
public class LoginRegisterController {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    com.sjsu.cmpe275.netflix.service.EmailService emailService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionAttributeStore sessionAttributeStore = new DefaultSessionAttributeStore();

    @PostMapping(value = "/register", produces = "application/json")
    private ResponseEntity<?> registerUser(@RequestBody Map map) throws UnsupportedEncodingException {
        String email = (String) map.get("email");
        String name = (String) map.get("name");
        String contact_no = (String) map.get("contact_no");
        String city = (String) map.get("city");
        String password = (String) map.get("password");
        Date date = Date.valueOf(java.time.LocalDate.now());

        Optional<UserDetailsModel> userOptional = userDetailsRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
            String activationCode = String.valueOf(new Random(System.nanoTime()).nextInt(100000));
            UserDetailsModel user = new UserDetailsModel(email, name, contact_no, city, date, encodedPassword, Boolean.FALSE, activationCode);
            userDetailsRepository.save(user);
            String url = "http://ec2-52-53-167-184.us-west-1.compute.amazonaws.com:8080/user/activate/" + email + "/" + activationCode;
            String text = "Your verification code is " + activationCode + "\n Or Click on the below link to activate your account. \n" + url;
            emailService.sendInvitationForUser(email, "Verification email for Movie Central", text);
            
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BadHttpRequest(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/activate/{email}/{code}", produces = "application/json")
    private ResponseEntity<?> activateUser(@PathVariable("email") String email,@PathVariable("code")  String activationCode, HttpSession session) throws UnsupportedEncodingException {

        Optional<UserDetailsModel> userOptional = userDetailsRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserDetailsModel user = userOptional.get();
            if (user.getVerificationCode().equals(activationCode)) {
                logger.info("Activation code", activationCode);
                user.setActivated(true);
                userDetailsRepository.save(user);
                session.setAttribute("userEmail", user.getEmail());
                Map<String, String> message = new HashMap<>();
                message.put("message", "Congratulations! Your account has been verified.");
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BadHttpRequest(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new BadHttpRequest(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> userLogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        Optional<UserDetailsModel> userOptional = userDetailsRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UserDetailsModel user = userOptional.get();
            logger.info("String password {} ----- {}", new String(Base64.getDecoder().decode(user.getPassword().getBytes())), password);
            System.out.println();
            if (password.equalsIgnoreCase(new String(Base64.getDecoder().decode(user.getPassword().getBytes())))) {
                if (user.isActivated()) {
                    session.setAttribute("userEmail", user.getEmail());
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @SuppressWarnings("deprecation")
	@GetMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<?> userLogout(WebRequest request, SessionStatus status,HttpSession session) {
        session.removeValue("userEmail");
        //session.getParameter("userEmail");
        return new ResponseEntity<>(new BadRequest(200,"You have have been logged out successfully"),HttpStatus.OK);

    }
    
    //Creating API for fb login
    @PostMapping(value = "/loginsocial", produces = "application/json")
    private ResponseEntity<?> loginSocialData(@RequestBody Map map, HttpSession session) throws UnsupportedEncodingException {
        String email = (String) map.get("email");
        String name = (String) map.get("name");
        String contact_no = "6691234567";
        String city = "San Jose";
        String password = "1234";
        Date date = Date.valueOf(LocalDate.now());
        System.out.println(date);
        
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        String activationCode = String.valueOf(new Random(System.nanoTime()).nextInt(100000));
        UserDetailsModel user = new UserDetailsModel(email, name, contact_no, city, date, encodedPassword, Boolean.TRUE, activationCode);
        userDetailsRepository.save(user);
        session.setAttribute("userEmail", user.getEmail());

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
