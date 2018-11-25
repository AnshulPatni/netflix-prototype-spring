package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sjsu.cmpe275.netflix.model.SubscriptionModel;
//import org.json.JSONObject;

@Repository
@Transactional
public interface SubscriptionRepository extends CrudRepository<SubscriptionModel, Integer> {
	
//	@Query("SELECT * FROM Subscription s WHERE s.email = :email")
//	Subscription getSubscriptionDetails(@Param("email") String email);
	
	@Query("SELECT s.subscriptionEndDate FROM SubscriptionModel s WHERE s.email = :email")
	Date getSubscriptionEndDate(@Param("email") String email);
	
	@Query("SELECT s.subscriptionStartDate FROM SubscriptionModel s WHERE s.email = :email")
	Date getSubscriptionStartDate(@Param("email") String email);
	
	@Modifying
	@Query("UPDATE SubscriptionModel s set s.subscriptionStartDate = :subscriptionStartDate WHERE s.email = :email")
	void updateSubscriptionStartDate(@Param("email") String email, @Param("subscriptionStartDate") Date subscriptionStartDate);
	
	@Modifying
	@Query("UPDATE SubscriptionModel s set s.subscriptionEndDate = :subscriptionEndDate WHERE s.email = :email")
	void updateSubscriptionEndDate(@Param("email") String email, @Param("subscriptionEndDate") Date subscriptionEndDate);
	
}
