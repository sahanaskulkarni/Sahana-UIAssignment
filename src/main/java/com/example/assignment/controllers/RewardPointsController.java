package com.example.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public int getRewardPointsByCustomer(@RequestBody DTO dto) {
        return rewardpointsservice.getTotalPointsByCustomer(dto.getCustomerId());
    }
	
	//points for a month
//	@GetMapping("/customer/pointPerMonth/{customerId}/{month}/{year}")
//    public int getPointsByCustomerPerMonth(@PathVariable Long customerId,@PathVariable int month, @PathVariable int year) {
//		System.out.println("test");
//		return rewardpointsservice.getMonthlyPoints(customerId, month, year);
//	}
	
	@GetMapping("/detailedRewards")
	public List<YearDTO> getMonthlyPointsByCustomer(@RequestBody DTO dto) {
          return rewardpointsservice.getMonthly(dto.getCustomerId());
    }

	
	//get all
	@GetMapping("/allRewards")
    public List<RewardPoint> getAllRewardPoints() {
        return rewardpointsservice.getAllPoints();
    }
	
}
