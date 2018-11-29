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
public class UserActivityModel 
{
	@Id
	@GeneratedValue
	@Column(name="user_activity_id")
	private int userActivityId;
	@Column(name="Email")
	private String Email;
	@Column(name="Title")
	private String Title;
	@Column(name="date")
	private Date date;
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



	public UserActivityModel(int userActivityId, String email, String title, Date date) {
		super();
		this.userActivityId = userActivityId;
		Email = email;
		Title = title;
		this.date = date;
	}
	
	
	public UserActivityModel( String email, String title, Date date) {
		super();
		Email = email;
		Title = title;
		this.date = date;
	}

	//private String location;
	//private Date Birth_date;
	
	public int getId() {
		return userActivityId;
	}


	public void setId(int userActivityId) {
		this.userActivityId = userActivityId;
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


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public UserActivityModel()
	{}

	
	

	

	
	

}
