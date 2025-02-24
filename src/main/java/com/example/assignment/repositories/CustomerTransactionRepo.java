package com.example.assignment.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.assignment.model.CustomerTransaction;

import jakarta.transaction.Transactional;

public interface CustomerTransactionRepo extends JpaRepository<CustomerTransaction, Long> {
	
	 List<CustomerTransaction> findByCustomerId(Long customerId);
	 
	 @Modifying
	 @Transactional
	 @Query(value ="delete from customer_transaction where id= :transactionId", nativeQuery = true)
	 int deleteByTransactionId(@Param("transactionId") Long transactionId);
	  
	  
	  @Query(value = "SELECT * FROM customer_transaction t WHERE t.customer_id = :customerId AND t.date BETWEEN :startDate AND :endDate", nativeQuery = true)
	    List<CustomerTransaction> findTransactionsByCustomerIdAndDateForPoints(
	        @Param("customerId") Long customerId,
	        @Param("startDate") LocalDate startDate,
	        @Param("endDate") LocalDate endDate
	    );
	  
	  @Modifying
	  @Transactional
	  @Query(value ="UPDATE public.customer_transaction SET amount = CASE WHEN :amount <> 0 THEN :amount ELSE amount END,spentdetails = CASE WHEN :spentdetails IS NOT NULL THEN :spentdetails ELSE spentdetails END,date = CASE WHEN :date IS NOT NULL THEN :date ELSE date END where customer_id= :customerId and id= :transactionId", nativeQuery = true)
	  int updateTransaction(
				@Param("amount") double amount,
				@Param("spentdetails") String spentdetails,
		        @Param("date") String date,
		        @Param("customerId") Long customerId,
		        @Param("transactionId") Long transactionId
		    );

	  
}
