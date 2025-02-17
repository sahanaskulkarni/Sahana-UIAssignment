package com.example.assignment.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.Customer;
import com.example.assignment.repositories.CustomerRepo;

@Service
public class CustomerService {
	@Autowired
	CustomerRepo customerRepo;
	
	public Customer saveCustomer(Customer customer) {
		customerRepo.save(customer);
		return customer;
	}

	public Customer getCustomerById(Long customerId) {
		return customerRepo.findById(customerId).get();
	}

}
