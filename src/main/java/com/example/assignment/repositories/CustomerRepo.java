package com.example.assignment.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assignment.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
	Optional<Customer> findByEmail(String email);
}

