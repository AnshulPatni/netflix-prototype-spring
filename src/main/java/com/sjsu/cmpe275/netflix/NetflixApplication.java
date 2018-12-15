package com.sjsu.cmpe275.netflix;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetflixApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(NetflixApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub	
	}
}
