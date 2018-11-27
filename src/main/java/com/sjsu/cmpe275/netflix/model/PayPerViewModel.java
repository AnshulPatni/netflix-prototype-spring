package com.sjsu.cmpe275.netflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payPerView")
public class PayPerViewModel {

	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="title")
	private String title;
	
	@Column(name="status")
	private String status;

	public String getEmail() {
		return email;
	}
	
	public PayPerViewModel() {
		
	}

	public PayPerViewModel(String email, String title, String status) {
		super();
		this.email = email;
		this.title = title;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
