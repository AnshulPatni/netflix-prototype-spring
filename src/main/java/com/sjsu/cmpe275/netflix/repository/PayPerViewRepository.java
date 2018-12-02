package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sjsu.cmpe275.netflix.model.PayPerViewModel;

@Repository
@Transactional
public interface PayPerViewRepository extends CrudRepository<PayPerViewModel, Integer> {

	@Query("SELECT p.status FROM PayPerViewModel p WHERE p.email = :email")
	String getPayPerViewStatus(@Param("email") String email);
	
	@Modifying
	@Query("UPDATE PayPerViewModel p set p.status = :status WHERE p.email = :email")
	void updatePayPerViewStatus(@Param("email") String email, @Param("status") String status);
	
	@Modifying
	@Query(value = "INSERT INTO pay_per_view (email, title, status) VALUES (:email, :title, :status)", nativeQuery = true)
	@Transactional
	void insertPayPerView(@Param("email") String email, @Param("title") String title, @Param("status") String status);

//    //Get unqiue payperviewuser
//	@Query("SELECT u.email FROM UserActivityModel u WHERE u.date <= :date AND u.movieAvailabilty LIKE 'perperview%'")
//	List getUniquePayPerViewUser(@Param("date") Date date);


	//	//Getting number of unique pay per view users
	@Query("SELECT DISTINCT COUNT(u.email) FROM UserActivityModel u WHERE u.date >= :startDate AND u.date <= :endDate AND u.availability LIKE '%PayPerView%'")
	int getUniquePayPerViewUser(@Param("startDate") Date startDate ,@Param("endDate") Date endDate);
}
