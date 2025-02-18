package com.example.assignment.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.assignment.model.CustomerTransaction;
import com.example.assignment.model.RewardPoint;

import jakarta.transaction.Transactional;

public interface RewardPointRepo extends JpaRepository<RewardPoint, Long> {
	
	 List<RewardPoint> findByCustomerId(Long customerId);
	 
	 @Query(value="select * from reward_point r where r.customer_id= :customerId", nativeQuery = true)
	 List<RewardPoint> getMonthlyByCustId(@Param("customerId") Long customerId);

	 @Modifying
	 @Transactional
	  @Query(value ="UPDATE public.reward_point SET points = CASE WHEN :points IS NOT NULL THEN :points ELSE points END where customer_id= :customerId and transaction_id= :transactionId", nativeQuery = true)
	  int updateRewards(
			  @Param("month") Object month,
			  @Param("year") Object year,
		        @Param("points") int points,
		        @Param("customerId") Long customerId,
		        @Param("transactionId") Long transactionId
		    );

	 
	 @Modifying
	 @Transactional
	 @Query(value ="delete from reward_point where transaction_id= :transactionId", nativeQuery = true)
	 int deleteByTrans(@Param("transactionId") Long transactionId);
	 
	 
	 
	 
	 
}
