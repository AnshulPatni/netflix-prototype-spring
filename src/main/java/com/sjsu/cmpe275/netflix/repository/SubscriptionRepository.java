package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.sjsu.cmpe275.netflix.model.SubscriptionModel;
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
	
	@Modifying
    @Query(value = "INSERT into subscription (email, subscription_start_date, subscription_end_date) VALUES (:email,:subscriptionStartDate,:subscriptionEndDate)", nativeQuery = true)
    @Transactional
    void insertSubscriptionDetails(@Param("email") String email, @Param("subscriptionStartDate") Date subscriptionStartDate, @Param("subscriptionEndDate") Date subscriptionEndDate);



//	//Getting number of unique subscription users
	@Query("SELECT COUNT(DISTINCT s.email) FROM SubscriptionModel s WHERE s.subscriptionEndDate >= :startDate AND s.subscriptionEndDate < :endDate OR s.subscriptionStartDate >= :endDate AND s.subscriptionStartDate > :startDate OR s.subscriptionStartDate <= :startDate AND s.subscriptionEndDate >= :startDate")
	int getUniqueSubscriptionUser(@Param("startDate") Date startDate ,@Param("endDate") Date endDate);


}

