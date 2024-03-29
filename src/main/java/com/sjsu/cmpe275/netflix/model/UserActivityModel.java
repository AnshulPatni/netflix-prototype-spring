package com.sjsu.cmpe275.netflix.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

	
	@Column(name="email")
	private String email;
	
	@Column(name="title")
	private String title;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="availability")
	private String availability;
	
	public UserActivityModel() {
		
	}

	public UserActivityModel(int userActivityId, String email, String title, Date date) {
		super();
		this.userActivityId = userActivityId;
		this.email = email;
		this.title = title;
		this.date = date;
	}
	
	
	public UserActivityModel( String email, String title, Date date) {
		super();
		this.email = email;
		this.title = title;
		this.date = date;
	}
	
	
	
	public UserActivityModel(int userActivityId, String email, String title, Date date, String availability) {
		super();
		this.userActivityId = userActivityId;
		this.email = email;
		this.title = title;
		this.date = date;
		this.availability = availability;
	}

	public int getUserActivityId() {
		return userActivityId;
	}

	public void setUserActivityId(int userActivityId) {
		this.userActivityId = userActivityId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMovieAvailability() {
		return availability;
	}

	public void setMovieAvailability(String availability) {
		this.availability = availability;
	}

}
