package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.netflix.model.TransactionModel;

@Repository
@Transactional
public interface TransactionRepository extends CrudRepository<TransactionModel, Integer> {

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO transaction(email, amount, date, transaction_type) VALUES (:email, :amount, :date, :transactionType)", nativeQuery = true)
	void insertTransaction(@Param("email") String email, @Param("amount") int amount, @Param("date") Date date, @Param("transactionType") String transactionType);
	
	@Query("SELECT m FROM TransactionModel m")
	List<TransactionModel> getTransactions();
	
}
