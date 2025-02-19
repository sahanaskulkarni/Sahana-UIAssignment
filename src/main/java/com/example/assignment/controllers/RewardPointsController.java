package com.example.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.model.RewardPoint;
import com.example.assignment.services.DTO;
import com.example.assignment.services.RewardPointsService;
import com.example.assignment.services.YearDTO;

@RestController
@RequestMapping("/rewardpoints")
public class RewardPointsController {
	
	@Autowired
	RewardPointsService rewardpointsservice;
	
	
	//get total reward points
	@GetMapping("/totalpoints")
    public ResponseEntity<Integer> getRewardPointsByCustomer(@RequestBody DTO dto) {
		int totalPoints = rewardpointsservice.getTotalPointsByCustomer(dto.getCustomerId());
        return new ResponseEntity<Integer>(totalPoints,HttpStatus.OK);
    }
	
	
	@GetMapping("/detailedRewards")
	public ResponseEntity<List<YearDTO>> getMonthlyPointsByCustomer(@RequestBody DTO dto) {
		List<YearDTO> result = rewardpointsservice.getMonthly(dto.getCustomerId());
          return new ResponseEntity<List<YearDTO>>(result,HttpStatus.OK);
    }


	
}
