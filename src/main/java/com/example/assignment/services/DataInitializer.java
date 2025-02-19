package com.example.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.assignment.model.Roles;
import com.example.assignment.repositories.RolesRepo;

import jakarta.annotation.PostConstruct;

@Service
public class DataInitializer{
	
	@Autowired
	private RolesRepo rolesRepo;
	
	@PostConstruct
	public void init(){
		rolesRepo.save(new Roles(1,"ADMIN"));
		rolesRepo.save(new Roles(2,"USER"));
	}

}
