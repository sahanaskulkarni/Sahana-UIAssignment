package com.example.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.assignment.model.Customer;
import com.example.assignment.repositories.CustomerRepo;
import com.example.assignment.services.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	
	@Mock
	private CustomerRepo customerRepo;
	
	@InjectMocks
	private CustomerService customerService;
	
	@Test
	public void testSaveCustomer() {
		Customer customer = new Customer();
		when(customerRepo.save(Mockito.any())).thenReturn(customer);
		Customer addedCustomer = customerService.saveCustomer(customer);
		assertEquals(customer,addedCustomer);
	}
	
	@Test
	public void testgetCustomerById() {
		Optional<Customer> customer = Optional.of(new Customer());
		when(customerRepo.findById(Mockito.any())).thenReturn(customer);
		Optional<Customer> returnedCustomer = Optional.ofNullable(customerService.getCustomerById(Mockito.any()));
		assertEquals(customer,returnedCustomer);
	}
	
	
	

}
