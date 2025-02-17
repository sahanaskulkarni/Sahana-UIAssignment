package com.example.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assignment.model.Roles;


public interface RolesRepo extends JpaRepository<Roles, Integer> {
	Roles findByName(String name);
}
