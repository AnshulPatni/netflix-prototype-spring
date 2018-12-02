package com.sjsu.cmpe275.netflix.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transaction")
public class TransactionModel {
	
	@Id
	@Column(name="transaction_id")
	private int transactionId;
	
	@Column(name="email")
	private String email;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="transaction_type")
	private String transactionType;

	public TransactionModel() {
		
	}
	
	public TransactionModel(int transactionId, String email, int amount, Date date, String transactionType) {
		super();
		this.transactionId = transactionId;
		this.email = email;
		this.amount = amount;
		this.date = date;
		this.transactionType = transactionType;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
