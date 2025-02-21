package com.example.assignment.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.exceptions.RewardPointsException;
import com.example.assignment.exceptions.TransactionException;
import com.example.assignment.model.DTO;
import com.example.assignment.model.MPointsDTO;
import com.example.assignment.model.YearDTO;
import com.example.assignment.services.RewardPointsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/rewardpoints")
public class RewardPointsController {
	
	@Autowired
	RewardPointsService rewardpointsservice;
	
	
	//get total reward points
	@GetMapping("/totalpoints/{customerId}")
	@SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Integer> getRewardPointsByCustomer(@PathVariable Long customerId) {
		try {
			int totalPoints = rewardpointsservice.getTotalPointsByCustomer(customerId);
	        return new ResponseEntity<Integer>(totalPoints,HttpStatus.OK);
		} catch (Exception e) {
			throw new RewardPointsException("Unable to fetch total reward points");
		}
    }
	
	
	@GetMapping("/detailedRewards/{customerId}")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<List<YearDTO>> getMonthlyPointsByCustomer(@PathVariable Long customerId) {
		try {
		  List<YearDTO> result = rewardpointsservice.getDetailedRewards(customerId);
          return new ResponseEntity<List<YearDTO>>(result,HttpStatus.OK);
		} catch (Exception e) {
			throw new RewardPointsException("Unable to fetch monthly reward points");
		}
    }
	
	
}
