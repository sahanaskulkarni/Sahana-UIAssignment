package com.example.assignment.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.CustomerTransaction;
import com.example.assignment.model.RewardPoint;
import com.example.assignment.repositories.CustomerTransactionRepo;
import com.example.assignment.repositories.RewardPointRepo;

@Service
public class RewardPointsService {
	 @Autowired
	  private RewardPointRepo rewardPointRepository;
	 
	 @Autowired
	 private CustomerTransactionRepo customerTransactionRepo;
	
	public List<RewardPoint> getAllPoints() {
       return rewardPointRepository.findAll();
   }
	
	public int getTotalPointsByCustomer(Long customerId) {
		List<RewardPoint> lstcustomers =  rewardPointRepository.findByCustomerId(customerId);
		int totalRewardsOfCustomer=0;
		for(RewardPoint rewards:lstcustomers) {
			totalRewardsOfCustomer+=rewards.getPoints();
		}
		
		return totalRewardsOfCustomer;
   }
	
	public int calculateRewardPoints(double amount) {
       int points = 0;

       if (amount > 100) {
           points += (amount - 100) * 2;  
           amount = 100;  
       }

       if (amount > 50) {
           points += (amount - 50); 
       }

       return points;
   }

	public int getMonthlyPoints(Long customerId, int month, int year) {
		
		LocalDate startDate = LocalDate.of(year,month, 1);
       LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
     
       List<CustomerTransaction> transactions = customerTransactionRepo.findTransactionsByCustomerIdAndDateForPoints(customerId,startDate,endDate);
       int totalPoints = 0;
       for (var transaction : transactions) {
           totalPoints += calculateRewardPoints(transaction.getAmount());
       }
       
		return totalPoints;
	}

	
	public List<YearDTO> getMonthly(Long customerId) {
		List<RewardPoint> lstByCustId = rewardPointRepository.getMonthlyByCustId(customerId);
		List<YearDTO> yearList = new ArrayList<>();
		
		for(RewardPoint rewards:lstByCustId) {
			boolean isYearPresent = containsYear(yearList,rewards.getYear());
			if(isYearPresent) {
				ListIterator<YearDTO> yrItr = yearList.listIterator();
				  while(yrItr.hasNext()){
					  YearDTO year = yrItr.next();
					  if(year.getYear()==rewards.getYear()) {
						  ListIterator<MonthDTO> monItr = year.getData().listIterator();
						  
						  boolean isMonthPresent = containsMonth(year.getData(),rewards.getMonth());
						  if(isMonthPresent) {
							  while(monItr.hasNext()){
								  MonthDTO monDTO = monItr.next();
								  if(monDTO.getMonth()==rewards.getMonth()) {
									monDTO.setPoints(monDTO.getPoints()+rewards.getPoints());
								  }
								  
							  }
						  	}
							  else {
									MonthDTO monthDTO = new MonthDTO();
									monthDTO.setMonth(rewards.getMonth());
									monthDTO.setPoints(rewards.getPoints());
									
									List<MonthDTO> monLst = year.getData();
									monLst.add(monthDTO);
									year.setData(monLst);
									
								  }
					  }
				  }
				  
			}
			else {
					YearDTO yearDTO = new YearDTO();
					yearDTO.setYear(rewards.getYear());
					
					List<MonthDTO> monthList = new ArrayList<>();
					MonthDTO monthDTO = new MonthDTO();
					monthDTO.setMonth(rewards.getMonth());
					monthDTO.setPoints(rewards.getPoints());
					monthList.add(monthDTO);
					yearDTO.setData(monthList);
					yearList.add(yearDTO);
					
				}
		
		}
		return yearList;
		
	}
	
	
	public boolean containsYear(List<YearDTO> list, int year) {
		if(!list.isEmpty()) {
		    for (YearDTO yr : list) {
		    	if((yr.getYear()==year)) {
		    		return true;
		    	}
		    }
		}else {
			return false;
		}
	    return false;
	}
	
	public boolean containsMonth(List<MonthDTO> list, int month) {
		if(!list.isEmpty()) {
		    for (MonthDTO mon : list) {
		    	if((mon.getMonth()==month)) {
		    		return true;
		    	}
		    }
		}else {
			return false;
		}
	    return false;
	}
	
	
}
