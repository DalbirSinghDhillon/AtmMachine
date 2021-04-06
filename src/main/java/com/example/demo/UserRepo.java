package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	//@Query(value = "SELECT account_Number FROM user WHERE account_Number= :variable ",nativeQuery = true)
	//int findByAccountNumber(@Param("variable")int accountNumber);
	
	@Query(value = "SELECT * FROM user WHERE account_Number= :variable ",nativeQuery = true)
	User findByAccountNumber(@Param("variable")int accountNumber);
	
	
}
