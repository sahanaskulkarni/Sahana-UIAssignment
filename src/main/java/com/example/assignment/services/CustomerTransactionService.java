package com.example.assignment.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.exceptions.CustomerNotFoundException;
import com.example.assignment.model.Customer;
import com.example.assignment.model.CustomerTransaction;
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
	 
	 
	public List<CustomerTransaction> getTransactionsByCustomer(Long customerId) {
       return transactionRepository.findByCustomerId(customerId);
   }

	public DTO addTransaction(DTO dto) {
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
		 
		 return savedTransaction;
	 }
	 
	 public String deleteTransaction(Long transactionId) {
		   transactionRepository.deleteByTransactionId(transactionId);
		 	rewardPointService.deleteByTransactionId(transactionId);
	        return "Transaction deleted successfully";
	    }
	 

	public String updateTransaction(DTO dto) {
		try {
			transactionRepository.updateTransaction(dto.getTransAmount(),dto.getTransSpentDetails(),dto.getTransDate(),dto.getCustomerId(),dto.getTransactionId());
			try {
				LocalDate toSetDate;
				int points = rewardPointService.calculateRewardPoints(dto.getTransAmount());
				if(dto.getTransDate()!=null) {
					toSetDate = LocalDate.parse(dto.getTransDate());
					rewardpointrepo.updateRewards(toSetDate.getMonthValue(),toSetDate.getYear(),points,dto.getCustomerId(),dto.getTransactionId());
				}
				else {
					rewardpointrepo.updateRewards(null,null,points,dto.getCustomerId(),dto.getTransactionId());
				}
			}
			catch(Exception ex) {}
		}
		catch(Exception e) {}

		return "updated";
		
	}
}
