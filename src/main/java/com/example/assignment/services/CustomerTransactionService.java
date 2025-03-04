package com.example.assignment.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.exceptions.CustomerNotFoundException;
import com.example.assignment.model.AddTransDTO;
import com.example.assignment.model.Customer;
import com.example.assignment.model.CustomerTransaction;
import com.example.assignment.model.DTO;
import com.example.assignment.model.MPointsDTO;
import com.example.assignment.model.RewardPoint;
import com.example.assignment.repositories.CustomerRepo;
import com.example.assignment.repositories.CustomerTransactionRepo;
import com.example.assignment.repositories.RewardPointRepo;

@Service
public class CustomerTransactionService {
	
	 @Autowired
	  private CustomerTransactionRepo transactionRepository;
	 
	 @Autowired
	  private CustomerRepo customerRepository;
	 
	 @Autowired
	  private RewardPointsService rewardPointService;
	 
	 @Autowired
	 RewardPointRepo rewardpointrepo;
	 
	 
	public List<DTO> getTransactionsByCustomer(Long customerId) {
		List<CustomerTransaction> lst = transactionRepository.findByCustomerId(customerId);
		
		List<DTO> result = new ArrayList<>(); 
		for(CustomerTransaction customerTransaction: lst) {
			DTO dto = new DTO();
			dto.setTransactionId(customerTransaction.getId());
			dto.setTransSpentDetails(customerTransaction.getSpentdetails());
			dto.setTransAmount(customerTransaction.getAmount());
			dto.setTransDate(customerTransaction.getDate());
			dto.setCustomerId(customerTransaction.getCustomer().getId());
			int totalPoints = rewardPointService.getTotalPointsByCustomer(dto.getCustomerId());
			dto.setCustomerTotalPoints(totalPoints);
			List<MPointsDTO> monthlyLst =  rewardPointService.getMonthlyPoints(dto.getCustomerId());
			dto.setMonthlyPointsData(monthlyLst);
			result.add(dto);
		}
		
	
       return result;
   }

	public DTO addTransaction(AddTransDTO dto) {
		 Customer customer = customerRepository.findById(dto.getCustomerId())
				 .orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
		 
		 CustomerTransaction transaction = new CustomerTransaction();
		 transaction.setAmount(dto.getTransAmount());
		 transaction.setSpentdetails(dto.getTransSpentDetails());
		 transaction.setDate(dto.getTransDate());
		 transaction.setCustomer(customer);
		 CustomerTransaction saved = transactionRepository.save(transaction);
		 
		 //for reward points
		 Integer points = rewardPointService.calculateRewardPoints(transaction.getAmount());
		 RewardPoint rewards = new RewardPoint();
		 LocalDate toSetDate = LocalDate.parse(transaction.getDate());
		 rewards.setMonth(toSetDate.getMonthValue());
		 rewards.setYear(toSetDate.getYear());
		 rewards.setCustomer(customer);
		 rewards.setTransaction(transaction);
		 rewards.setPoints(points);
		 rewardpointrepo.save(rewards);
		 
		 
		 DTO savedTransaction = new DTO();
		 savedTransaction.setCustomerId(dto.getCustomerId());
		 savedTransaction.setTransactionId(saved.getId());
		 savedTransaction.setTransAmount(saved.getAmount());
		 savedTransaction.setTransSpentDetails(saved.getSpentdetails());
		 savedTransaction.setTransDate(saved.getDate());
		 int totalPoints = rewardPointService.getTotalPointsByCustomer(dto.getCustomerId());
		 savedTransaction.setCustomerTotalPoints(totalPoints);
		 
		 List<MPointsDTO> monthlyLst =  rewardPointService.getMonthlyPoints(dto.getCustomerId());
		 savedTransaction.setMonthlyPointsData(monthlyLst);
		 
		 return savedTransaction;
	 }
	 
	
	 public String deleteTransaction(Long transactionId) {
		 	rewardPointService.deleteByTransactionId(transactionId);
		    transactionRepository.deleteByTransactionId(transactionId); 	
	        return "Transaction deleted successfully";
	    }
	 
	
	public String updateTransaction(DTO dto) {
		try {
			CustomerTransaction saved = transactionRepository.findById(dto.getTransactionId())
					.orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
			
			RewardPoint rewardSaved = rewardpointrepo.findByCustomerIdAndTransactionId(dto.getCustomerId(),dto.getTransactionId());
			
			if(dto.getTransAmount()!=0.0 && dto.getTransAmount()!=saved.getAmount()) {
				saved.setAmount(dto.getTransAmount());
				int points = rewardPointService.calculateRewardPoints(dto.getTransAmount());
				rewardSaved.setPoints(points);
			}
			if (dto.getTransSpentDetails()!=null && !dto.getTransSpentDetails().equals(saved.getSpentdetails())) {
				saved.setSpentdetails(dto.getTransSpentDetails());
			}
			if (dto.getTransDate()!=null && !dto.getTransDate().equals(saved.getDate())) {
				saved.setDate(dto.getTransDate());
				LocalDate toSetDate = LocalDate.parse(dto.getTransDate());
				rewardSaved.setMonth(toSetDate.getMonthValue());
				rewardSaved.setYear(toSetDate.getYear());
			}
			
			transactionRepository.save(saved);
			rewardpointrepo.save(rewardSaved);
			
		}
		catch(Exception e) {}

		return "updated";
		
	}
	
	
	
}
