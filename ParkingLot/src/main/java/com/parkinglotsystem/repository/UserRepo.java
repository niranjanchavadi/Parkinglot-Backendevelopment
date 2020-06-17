package com.parkinglotsystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parkinglotsystem.model.User;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
	@Query(value = "Select * from parking where email = :email", nativeQuery = true)
	User findEmail(String email);

	@Query(value = "select * from parking where email=?", nativeQuery = true)
	User findByEmail(String userMail);

	@Query(value = "select * from parking where user_id = :id", nativeQuery = true)
	User findById(long id);
	
	@Modifying
	@Transactional
	@Query(value = "update parking set modified_Date = ? where user_id = ?", nativeQuery = true)
	void modifiedDate(LocalDateTime date, long id);

	@Modifying
	@Query(value = "update parking set is_verified = true where user_id = :id", nativeQuery = true)
	void verify(long id);
	
	@Query(value = "SELECT EMAIL from  parkinglotsystem.parking where actor_type in  ('OWNER','POLICEMEN','SECURITYSTAFF') ", nativeQuery = true)
	 List<String> getallEmail();
	
	
}
