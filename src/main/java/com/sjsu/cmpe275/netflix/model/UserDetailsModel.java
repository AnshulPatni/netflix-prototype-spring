package com.sjsu.cmpe275.netflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Column(name="date")
	private Date date;
	
	@Column(name="password")
	private String password;

	@Column(name="is_activated")
	@JsonIgnore
	private boolean isActivated;

	@Column(name="verification_code")
	@JsonIgnore
	private String verificationCode;

	public UserDetailsModel(String email, String name, String contactNo, String city, Date date, String password, boolean isActivated, String verificationCode) {
		this.email = email;
		this.name = name;
		this.contactNo = contactNo;
		this.city = city;
		this.date = date;
		this.password = password;
		this.isActivated = isActivated;
		this.verificationCode = verificationCode;
	}


	public UserDetailsModel() {
		
	}
	
	public UserDetailsModel(String email, String name, String contactNo, String city, Date date, String password, Boolean isActivated, String verificationCode ) {
		super();
		this.email = email;
		this.name = name;
		this.contactNo = contactNo;
		this.city = city;
		this.date = date;
		this.password = password;
		this.isActivated = isActivated;
		this.verificationCode = verificationCode;
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

	public String getPassword() {
		return password;
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




	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean activated) {
		isActivated = activated;
	}


	public String getVerificationCode() {
		return verificationCode;
	}


}
