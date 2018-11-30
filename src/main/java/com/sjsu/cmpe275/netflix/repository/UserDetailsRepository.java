package com.sjsu.cmpe275.netflix.repository;

import java.util.List;

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
	
}
