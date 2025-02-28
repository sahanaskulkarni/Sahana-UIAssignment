package com.example.assignment.repositories;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.assignment.model.MPointsDTO;
import com.example.assignment.model.RewardPoint;

import jakarta.transaction.Transactional;

public interface RewardPointRepo extends JpaRepository<RewardPoint, Long> {
	
	 List<RewardPoint> findByCustomerId(Long customerId);
	 
	 RewardPoint findByCustomerIdAndTransactionId(Long customerId, Long transactionId);
	 
	 @Query(value="select * from reward_point r where r.customer_id= :customerId", nativeQuery = true)
	 List<RewardPoint> getDetailedRewardsByCustId(@Param("customerId") Long customerId);
	 
	 @Modifying
	 @Transactional
	 @Query(value ="delete from reward_point where transaction_id= :transactionId", nativeQuery = true)
	 int deleteByTrans(@Param("transactionId") Long transactionId);

	 
	 @Query(value="SELECT t.year,t.month, SUM(t.points) as points FROM reward_point t WHERE t.customer_id= :customerId group by t.year,t.month order by t.year desc,t.month desc", nativeQuery = true)
	 List<MPointsDTO> getMonthly(@Param("customerId") Long customerId);

}
