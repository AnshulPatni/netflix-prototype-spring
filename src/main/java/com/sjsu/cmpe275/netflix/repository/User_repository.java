package com.sjsu.cmpe275.netflix.repository;

//import java.sql.Date;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.netflix.model.User;


@Repository
@Transactional
public interface User_repository extends CrudRepository<User, Integer> {

/*	public default int getData(String title)
	{
		String sql = "SELECT d.Email FROM Defaults d where d.Title ="+title+"";
		Query q = createQuery(sql);
		long count = (long)q.getSingleResult();
		return;
	}
*/	
	
	@Query("SELECT u.Email FROM User u WHERE u.Title = :title")
	String getData(@Param("title") String title);
	
	@Query("SELECT u.Title FROM User u WHERE u.id = :id")
	String getName(@Param("id") Integer id);
	
	@Modifying
	@Query("update User u set u.timestamp = :status where u.id = :id")
	int updateUserSetStatusForName(@Param("id") int id, @Param("status") java.util.Date status);
	
	
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
