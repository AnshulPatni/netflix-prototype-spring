package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sjsu.cmpe275.netflix.model.Subscription;
//import org.json.JSONObject;

@Repository
@Transactional
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
	
	@Query("SELECT s.subscriptionEndDate FROM Subscription s WHERE s.email = :email")
	Date getSubscriptionEndDate(@Param("email") String email);
	
	@Query("SELECT s.subscriptionStartDate FROM Subscription s WHERE s.email = :email")
	Date getSubscriptionStartDate(@Param("email") String email);
	
//	@Modifying
//	@Query("update Subscription s set s.subscriptionStartDate = :subscriptionStartDate where s.email = :email")
//	void updateSubscriptionStartDate(@Param("email") String email, @Param("subscription_start_date") java.util.Date subscription_start_date);
	
//	@Query("SELECT u.Title FROM User u WHERE u.id = :id")
//	String getName(@Param("id") Integer id);
//	
//	@Modifying
//	@Query("update Subscription s set s.subscriptionStartDate = :subscriptionStartDate where s.email = :email")
//	void updateSubscriptionDetails(@Param("email") int email, @Param("subscription_start_date") java.util.Date subscription_start_date);
	
}
