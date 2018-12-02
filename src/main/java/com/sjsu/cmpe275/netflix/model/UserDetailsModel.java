package com.sjsu.cmpe275.netflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "user_details")
public class UserDetailsModel {
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="name")
	private String name;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;

	@Column(name="date")
	private Date date;


	public UserDetailsModel() {
		
	}
	
	public UserDetailsModel(String email, String name, String contactNo, String city, String state) {
		super();
		this.email = email;
		this.name = name;
		this.contactNo = contactNo;
		this.city = city;
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
