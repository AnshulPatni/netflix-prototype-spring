package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sjsu.cmpe275.netflix.model.UserDetailsModel;

@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserDetailsModel, Integer> {

	@Query("SELECT u FROM UserDetailsModel u WHERE u.email = :email")
	UserDetailsModel getUserDetails(@Param("email") String email);


	//For TotalUniqueUser
	@Query("SELECT DISTINCT COUNT(u.email) FROM UserDetailsModel u WHERE u.date LIKE CONCAT('%-',:date,'-%')")
	int getTotalUniqueUser(@Param("date") int date);

	//For TotalUniqueActiveUser
	@Query("SELECT DISTINCT COUNT(u.email) FROM UserActivityModel u WHERE u.date LIKE CONCAT('%-',:date,'-%')")
	int getTotalActiveUniqueUser(@Param("date") int date);

//	Optional<UserDetailsModel> findByEmail(String email);
//	Optional<UserDetailsModel> findByEmailAndPassword(String email, String password);
}
