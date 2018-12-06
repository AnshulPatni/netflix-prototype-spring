package com.sjsu.cmpe275.netflix.repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

	//@Query("SELECT u FROM User u WHERE u.Title = :title")
	//String getDatabyTitle(@Param("title") String title);
	//@Query(value = "SELECT * FROM user_activity u WHERE u.Title = title", nativeQuery = true)
	//Collection<User> findAllActiveUsersNative();
	//Collection<User> getDatabyTitle(@Param("title") String title);
	
	@Query("SELECT u FROM UserActivityModel u WHERE u.title = :title")
	List findAllActiveUsers(@Param("title") String title);
	
	
	@Query("SELECT count(*) FROM UserActivityModel u WHERE u.date >= :time and u.title = :title")
	int getDatabyNameAndPeriod(@Param("time") Date time, @Param("title") String title);
	
//	@Modifying
//	@Query("update UserActivityModel u set u.timestamp = :status where u.id = :id")
//	int updateUserSetStatusForName(@Param("id") int id, @Param("status") java.util.Date status);

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
	
    /*Query query = entityManager.createNativeQuery("INSERT INTO topic (ID, TITLE,CREATION_DATE) " +
            " VALUES(?,?,?)");
        query.setParameter(1, id);
        query.setParameter(2, title);
        query.setParameter(3, creationDate);
        query.executeUpdate();
        
        */
	/*@Query("Insert into User  values(Email, Title, status)")
	int Insertdata(@Param("Email") String Email, @Param("Title") String Title,@Param("status") java.util.Date status);
*/
	/*SELECT COUNTRY, count(*) 
	FROM DRUG_SEIZURE 
	WHERE COUNTRY IS NOT NULL 
	GROUP BY COUNTRY
	ORDER BY count(*) DESC
	LIMIT 3*/
//@Query(value="SELECT u.Email FROM User u WHERE u.Title = :title",nativeQuery=true)
//Optional<User> getData(@Param("title") String title);






	//For TotalUniqueActiveUser
	@Query("SELECT DISTINCT u.email FROM UserActivityModel u WHERE u.date LIKE CONCAT('%-',:date,'-%')")
	List getTotalUniqueActiveUser(@Param("date") Date date);





}
/*@Repository

public class User_repository 
{
	@PersistenceContext
	EntityManager entityManager;
	
	
	public User findById(int id)
	{
		return entityManager.find(User.class, id);
	}
	
	
	
}
*/
