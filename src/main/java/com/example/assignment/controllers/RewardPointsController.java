package com.example.assignment.controllers;


import java.util.List;
import java.util.Map;

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
import com.example.assignment.model.NewPointsDTO;
import com.example.assignment.model.RewardPoint;
import com.example.assignment.model.YearDTO;
import com.example.assignment.services.RewardPointsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/rewardpoints")
@SecurityRequirement(name = "bearerAuth")
public class RewardPointsController {
	
	@Autowired
	RewardPointsService rewardpointsservice;
	
	
	//get total reward points
//	@GetMapping("/totalpoints/{customerId}")
//	@Operation(summary = "Total reward points", description = "Total reward points of a customer are returned",
//			responses= {
//					@ApiResponse( description = "Success",responseCode = "200"),
//					@ApiResponse(description = "Unauthorized",responseCode = "403")
//				})
//    public ResponseEntity<Integer> getRewardPointsByCustomer(@PathVariable Long customerId) {
//		try {
//			int totalPoints = rewardpointsservice.getTotalPointsByCustomer(customerId);
//	        return new ResponseEntity<Integer>(totalPoints,HttpStatus.OK);
//		} catch (Exception e) {
//			throw new RewardPointsException("Unable to fetch total reward points");
//		}
//    }
//	
//	
//	@GetMapping("/detailedRewards/{customerId}")
//	@Operation(summary = "Details of reward points earned", description = "Detailed information of all reward points earned",
//			responses= {
//					@ApiResponse( description = "Success",responseCode = "200"),
//					@ApiResponse(description = "Unauthorized",responseCode = "403")
//				})
//	public ResponseEntity<List<YearDTO>> getMonthlyPointsByCustomer(@PathVariable Long customerId) {
//		try {
//		  List<YearDTO> result = rewardpointsservice.getDetailedRewards(customerId);
//          return new ResponseEntity<List<YearDTO>>(result,HttpStatus.OK);
//		} catch (Exception e) {
//			throw new RewardPointsException("Unable to fetch monthly reward points");
//		}
//    }
//	
	
	@Operation(summary = "Details of reward points earned", description = "Detailed information of all reward points earned",
			responses= {
					@ApiResponse( description = "Success",responseCode = "200"),
					@ApiResponse(description = "Unauthorized",responseCode = "403")
				})
	@GetMapping("/monthlyPoints/{customerId}/{year}/{month}")
	public  ResponseEntity<Map<Long, List<Map<String, Integer>>>> monthlyPoints(@PathVariable Long customerId,@PathVariable Long year,@PathVariable Long month) {
		try {
			Map<Long, List<Map<String, Integer>>> result = rewardpointsservice.getMonthlyRewards(customerId,year,month);
			return new ResponseEntity<Map<Long, List<Map<String, Integer>>>>(result,HttpStatus.OK);
		} catch (Exception e) {
			throw new RewardPointsException("Unable to fetch monthly reward points");
		}
	}
	
	
	
	
	
	
	
	
	
}
