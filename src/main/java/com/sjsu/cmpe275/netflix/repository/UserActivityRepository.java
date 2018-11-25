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

/*	public default int getData(String title)
	{
		String sql = "SELECT d.Email FROM Defaults d where d.Title ="+title+"";
		Query q = createQuery(sql);
		long count = (long)q.getSingleResult();
		return;
	}
*/	
	
	//@Query("SELECT u FROM User u WHERE u.Title = :title")
	//String getDatabyTitle(@Param("title") String title);
	//@Query(value = "SELECT * FROM user_activity u WHERE u.Title = title", nativeQuery = true)
	//Collection<User> findAllActiveUsersNative();
	//Collection<User> getDatabyTitle(@Param("title") String title);
	
	@Query("SELECT u FROM UserActivityModel u WHERE u.Title = :title")
	List findAllActiveUsers(@Param("title") String title);
	
	
	@Query("SELECT count(*) FROM UserActivityModel u WHERE u.timestamp < :time and u.Title = :title")
	int getDatabyNameAndPeriod(@Param("time") Date time, @Param("title") String title);
	
	@Modifying
	@Query("update UserActivityModel u set u.timestamp = :status where u.id = :id")
	int updateUserSetStatusForName(@Param("id") int id, @Param("status") java.util.Date status);

	@Query("SELECT u.Email FROM UserActivityModel u WHERE u.timestamp < :date GROUP BY Email ORDER BY count(*) DESC")
	List getTopTenUsers(@Param("date") Date date);
	
	@Query("SELECT u.Title FROM UserActivityModel u WHERE u.timestamp >= :date GROUP BY Title ORDER BY count(*) DESC")
	List getTopTenMovies(@Param("date") Date date);

	@Query("SELECT u.Title FROM UserActivityModel u WHERE u.Email = :userName ORDER BY timestamp DESC")
	List getMoviesHistoryByUser(@Param("userName") String userName);

	
	
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
