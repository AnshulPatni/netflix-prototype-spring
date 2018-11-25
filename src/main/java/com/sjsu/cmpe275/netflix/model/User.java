package com.sjsu.cmpe275.netflix.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "user_activity")
public class User 
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private int id;
	@Column(name="Email")
	private String Email;
	@Column(name="Title")
	private String Title;
	@Column(name="timestamp")
	
	private Date timestamp;
	//@Transient
	//private List<User> getToptenMovies;


	//private int status;
	//@Transient
	//private int noOfPlay;
	//@Transient
    //private List<User> getToptenUser;
	//public int getNoOfPlay() {
	//	return noOfPlay;
	//}



	public User(int id, String email, String title, Date timestamp) {
		super();
		this.id = id;
		Email = email;
		Title = title;
		this.timestamp = timestamp;
	}
	
	
	public User( String email, String title, Date timestamp) {
		super();
		Email = email;
		Title = title;
		this.timestamp = timestamp;
	}

	//private String location;
	//private Date Birth_date;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getTitle() {
		return Title;
	}


	public void setTitle(String title) {
		Title = title;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public User()
	{}

	
	

	

	
	

}
