package com.example.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.example.assignment.model.DTO;
import com.example.assignment.repositories.CustomerRepo;
import com.example.assignment.repositories.CustomerTransactionRepo;
import com.example.assignment.repositories.RewardPointRepo;
import com.example.assignment.services.CustomerTransactionService;
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
	
	@Mock
	RewardPointRepo rewardPointRepository;

	
	
	@Test
    public void testgetTransactionsByCustomer() {
		List<CustomerTransaction> mocked = new ArrayList<>();
		when(transactionRepository.findByCustomerId(Mockito.any())).thenReturn(mocked);
		assertEquals(customerTransactionService.getTransactionsByCustomer(Mockito.any()),Collections.EMPTY_LIST);
	}
	
	
	@Test
    public void testdeleteTransaction() {
		assertEquals(rewardPointService.deleteByTransactionId(Mockito.anyLong()), 0);
		when(transactionRepository.deleteByTransactionId(Mockito.anyLong())).thenReturn(0);
		assertEquals(customerTransactionService.deleteTransaction(Mockito.anyLong()),"Transaction deleted successfully");
		
	}
	
	@Test
	public void testaddTransaction() {

		Optional<Customer> customer = Optional.of(new Customer());
		when(customerRepository.findById(Mockito.any())).thenReturn(customer);
		
		CustomerTransaction transaction = new CustomerTransaction();

		when(transactionRepository.save(Mockito.any())).thenReturn(transaction);

		DTO dto = new DTO();
		dto.setTransDate("2022-10-10");
		DTO returnedDTO = new DTO();
		DTO expectedDTO = customerTransactionService.addTransaction(dto);
		assertEquals(expectedDTO.getTransAmount(),returnedDTO.getTransAmount());
	}
	
	@Test
	public void testupdateTransaction() {
		List<CustomerTransaction> lst = new ArrayList<>();
		when(transactionRepository.updateTransaction(0, null, null, null, null)).thenReturn(0);
		DTO dto = new DTO();
		assertEquals(customerTransactionService.updateTransaction(dto),"updated");
	}
	
	
	
	
	
}
