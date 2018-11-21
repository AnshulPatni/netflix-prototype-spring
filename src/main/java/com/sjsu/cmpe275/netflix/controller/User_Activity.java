package com.sjsu.cmpe275.netflix.controller;



//import com.sjsu.cmpe275.netflix.model.User;
//
//import java.sql.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
import java.util.Calendar;
//import java.util.Optional;

//import javax.persistence.SqlResultSetMapping;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275.netflix.repository.User_repository;

import javassist.tools.web.BadHttpRequest;
@RestController
@RequestMapping(value = "/user")
public class User_Activity {

//	private org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	User_repository repository;
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQuestionJson(@PathVariable("Id") String Id) {

        return getQuestion(Id);

    }

	private ResponseEntity<?> getQuestion(String id) {

        String questionOptional = repository.getData(id);
            
            return new ResponseEntity<>(questionOptional, HttpStatus.OK);

        }
	
	//get_user_activity_history,	get_top_users,	get_no_of_plays,	get_top_10_movies_on_plays,	play_movie,	get_top_movie_based_on_plays

	//User_activity
	@RequestMapping(value = "/name/{Id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQuestionJson1(@PathVariable("Id") String Id) {

        return getQuestion1(Id);

    }

	private ResponseEntity<?> getQuestion1(String id) {

        String questionOptional = repository.getName(Integer.parseInt(id));
            
            return new ResponseEntity<>(questionOptional, HttpStatus.OK);

        }
	//updateUserSetStatusForName update date
	
	
	@RequestMapping(value = "/c/{Id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQuestionJson2(@PathVariable("Id") String Id) {

        return getQuestion2(Id);

    }

	private ResponseEntity<?> getQuestion2(String id) {
        //LocalDateTime status = LocalDateTime.now();
		java.util.Date date = Calendar.getInstance().getTime();
        int questionOptional = repository.updateUserSetStatusForName(Integer.parseInt(id), date);
            if(questionOptional!=1)
            {
            	return new ResponseEntity<>(new BadHttpRequest(), HttpStatus.NOT_FOUND);
            } else {
                System.out.println("Update done");
            	return new ResponseEntity<>(questionOptional, HttpStatus.OK);
            }

        }
	/*if (!questionOptional.isPresent()) {
            return new ResponseEntity<>(new BadRequest(404, "Question with id " + questionId + " does not exist"), HttpStatus.NOT_FOUND);
        } else {
            // write to db
            Question question = questionOptional.get();
            questionRepository.delete(question);
            retu*/
	/*@RequestMapping(value = "/insert", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQuestionJson2() {

        return Insert();

    }
	private ResponseEntity<?> Insert() {
        //LocalDateTime status = LocalDateTime.now();
		String Email = "prasun.saxena";
		String Title = "Ram";
		java.util.Date date = Calendar.getInstance().getTime();
        int questionOptional = repository.Insertdata(Email, Title, date);
            if(questionOptional!=1)
            {
            	return new ResponseEntity<>(new BadHttpRequest(), HttpStatus.NOT_FOUND);
            } else {
                System.out.println("Update done");
            	return new ResponseEntity<>(questionOptional, HttpStatus.OK);
            }

        }
	
*/	
}
