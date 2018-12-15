package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.netflix.model.UserActivityModel;


@Repository
@Transactional
public interface UserActivityRepository extends CrudRepository<UserActivityModel, Integer> {
	
	@Query("SELECT u.email FROM UserActivityModel u WHERE u.title = :title")
	List findAllActiveUsers(@Param("title") String title);
	
	
	@Query("SELECT count(*) FROM UserActivityModel u WHERE u.date >= :time and u.title = :title")
	int getDatabyNameAndPeriod(@Param("time") Date time, @Param("title") String title);

	@Query("SELECT u.email FROM UserActivityModel u WHERE u.date >= :date GROUP BY email ORDER BY count(*) DESC")
	List getTopTenUsers(@Param("date") Date date);
	
	@Query("SELECT u.title FROM UserActivityModel u WHERE u.date >= :date GROUP BY title ORDER BY count(*) DESC")
	List getTopTenMovies(@Param("date") Date date);

	@Query("SELECT u.title FROM UserActivityModel u WHERE u.email = :userName ORDER BY date DESC")
	List getMoviesHistoryByUser(@Param("userName") String userName);
	
	@Query("SELECT title FROM UserActivityModel u WHERE u.email = :email")
	List getUserHistory(@Param("email") String email);
	
	@Modifying
	@Query(value = "INSERT into user_activity (email, title, date, availability) VALUE (:email,:title,:date, :availability)", nativeQuery = true)
	@Transactional
	void insertUserActivity(@Param("email") String email, @Param("title") String title, @Param("date") Date date, @Param("availability") String availability);

	//For TotalUniqueActiveUser
	@Query("SELECT DISTINCT u.email FROM UserActivityModel u WHERE u.date >= :startDate AND u.date < :endDate")
	List getTotalUniqueActiveUser(@Param("startDate") Date startDate ,@Param("endDate") Date endDate);
	
}
