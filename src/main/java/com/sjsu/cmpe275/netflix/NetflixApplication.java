package com.sjsu.cmpe275.netflix;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sjsu.cmpe275.netflix.repository.UserActivityRepository;;

@SpringBootApplication
public class NetflixApplication implements CommandLineRunner{

	
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public static void main(String[] args) {
		SpringApplication.run(NetflixApplication.class, args);
	}
	
	@Autowired
	UserActivityRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		 
	}
}
