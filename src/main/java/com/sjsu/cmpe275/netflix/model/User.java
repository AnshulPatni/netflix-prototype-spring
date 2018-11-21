package com.sjsu.cmpe275.netflix.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACTIVITY")
public class User 
{
	@Id
	@GeneratedValue
	@Column(name="user_activity_id")
	private int id;
	@Column(name="email")
	private String Email;
	@Column(name="title")
	private String Title;
	@Column(name="timestamp")
	private Date timestamp;
	
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
