package com.example.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.assignment.model.Customer;
import com.example.assignment.model.CustomerTransaction;
import com.example.assignment.repositories.CustomerRepo;
import com.example.assignment.repositories.CustomerTransactionRepo;
import com.example.assignment.repositories.RewardPointRepo;
import com.example.assignment.services.CustomerTransactionService;
import com.example.assignment.services.DTO;
import com.example.assignment.services.RewardPointsService;

@ExtendWith(MockitoExtension.class)
public class CustomerTransactionServiceTest {
	
	@InjectMocks
	CustomerTransactionService customerTransactionService;
	
	@Mock
	  private CustomerTransactionRepo transactionRepository;
	 
	@Mock
	  private CustomerRepo customerRepository;
	 
	@Mock
	  private RewardPointsService rewardPointService;
	 
	@Mock
	 RewardPointRepo rewardpointrepo;

	
	
	@Test
    public void testgetTransactionsByCustomer() {
		List<CustomerTransaction> mocked = new ArrayList<>();
		when(transactionRepository.findByCustomerId(Mockito.any())).thenReturn(mocked);
		assertEquals(customerTransactionService.getTransactionsByCustomer(Mockito.any()),Collections.EMPTY_LIST);
	}
	
	
	@Test
    public void testaddTransaction() {
//		 Optional<Customer> customer = Optional.of(new  Customer());
//		 when(customerRepository.findById(Mockito.any())).thenReturn(customer);
//		 CustomerTransaction transaction = new CustomerTransaction();
//		 when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
//		 when(rewardPointService.getTotalPointsByCustomer(Mockito.any())).thenReturn(10);
//		 DTO dto = new DTO();
//		 assertEquals(customerTransactionService.addTransaction(Mockito.any()),dto);
	}
	
	
	
	
	
	
	
	
}
