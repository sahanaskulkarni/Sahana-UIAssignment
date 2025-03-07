package com.example.assignment.services;


import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.MPointsDTO;
import com.example.assignment.model.MonthDTO;
import com.example.assignment.model.NewPointsDTO;
import com.example.assignment.model.RewardPoint;
import com.example.assignment.model.YearDTO;
import com.example.assignment.repositories.CustomerTransactionRepo;
import com.example.assignment.repositories.RewardPointRepo;

@Service
public class RewardPointsService {
	 @Autowired
	  private RewardPointRepo rewardPointRepository;
	 
	 
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

	
	public List<MPointsDTO> getMonthlyPoints(Long customerId) {
		List<MPointsDTO> lst = rewardPointRepository.getMonthly(customerId);
		List<MPointsDTO> newLst = new ArrayList<>();
		for(MPointsDTO mPointsDTO : lst) {
			String monthOfYear = Month.of(mPointsDTO.getMonth()).getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);
			MPointsDTO newMPointsDTO =  new MPointsDTO();
			newMPointsDTO.setPoints(mPointsDTO.getPoints());
			newMPointsDTO.setYear(mPointsDTO.getYear());
			newMPointsDTO.setMonthOfYear(monthOfYear);
			newLst.add(newMPointsDTO);
		}
		
		return newLst;
		
	}
	
	
	public List<YearDTO> getDetailedRewards(Long customerId) {
		List<RewardPoint> lstByCustId = rewardPointRepository.getDetailedRewardsByCustId(customerId);
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
	
	public boolean containsYearNew(List<NewPointsDTO> list, int year) {
		if(!list.isEmpty()) {
		    for (NewPointsDTO yr : list) {
		    	if((yr.getYear()==year)) {
		    		return true;
		    	}
		    }
		}else {
			return false;
		}
	    return false;
	}
	
	public boolean containsMonthNew(List<NewPointsDTO> list, int month) {
		if(!list.isEmpty()) {
		    for (NewPointsDTO mon : list) {
		    	if((mon.getMonth()==month)) {
		    		return true;
		    	}
		    }
		}else {
			return false;
		}
	    return false;
	}


	public int deleteByTransactionId(Long transactionId) {
		return rewardPointRepository.deleteByTrans(transactionId);
	}

	public Map<Long, List<Map<String, Integer>>> getMonthlyRewards(Long customerId, Long year, Long month) {
		
		List<NewPointsDTO> lstcustomers = getDetailedRewardsNew(customerId);

		lstcustomers.sort(Comparator.comparing(NewPointsDTO::getYear).thenComparing(NewPointsDTO::getMonth));
		
		NewPointsDTO endDateObj = lstcustomers.stream().filter((ele->(ele.getYear()==year) && ele.getMonth()==month)).findFirst().orElse(null);
		
		int indexOfEndDate = lstcustomers.indexOf(endDateObj);

		List<NewPointsDTO> threeMonthsLst =  lstcustomers.subList((indexOfEndDate-2), indexOfEndDate+1);

		 List<NewPointsDTO> copiedList = new ArrayList<>();
	        for (NewPointsDTO dto : threeMonthsLst) {
	            copiedList.add(new NewPointsDTO(dto)); 
	        }
		

		NewPointsDTO dto1 = threeMonthsLst.get(0);
		NewPointsDTO dto2 = threeMonthsLst.get(1);
		NewPointsDTO dto3 = threeMonthsLst.get(2);
		
		// for enddate as february
		if ((endDateObj.getMonth()==2) && (dto2.getYear()!=(dto3.getYear()))) {

			
			if ((dto2.getMonth()!=(dto3.getMonth()-1)) ) {
				dto2.setMonth(dto3.getMonth()-1);
				dto2.setPoints(0);
			}
			
			if (dto1.getMonth()!=12) {
				NewPointsDTO newObjDto = copiedList.stream().filter(n->(n.getMonth()==(12)) &&
																(n.getYear()==(dto3.getYear()-1))).findFirst().orElse(null);
				

				if (newObjDto!=null) {
					dto1.setMonth(newObjDto.getMonth());
					dto1.setPoints(newObjDto.getPoints());
				} else {
					dto1.setMonth(12);
					dto1.setPoints(0);
				}
			
			}
		}
		
		// for enddate as january
		if ((endDateObj.getMonth()==1) && (dto2.getYear()!=(dto3.getYear()))) {

			if ((dto2.getMonth()!=12) ) {
				dto2.setMonth(12);
				dto2.setPoints(0);
			}
			
			if (dto1.getMonth()!=11) {
				NewPointsDTO newObjDto = copiedList.stream().filter(n->(n.getMonth()==(11)) &&
																(n.getYear()==(dto3.getYear()-1))).findFirst().orElse(null);
				
				if (newObjDto!=null) {
					dto1.setMonth(newObjDto.getMonth());
					dto1.setPoints(newObjDto.getPoints());
				} else {
					dto1.setMonth(11);
					dto1.setPoints(0);
				}
			}
		}
		
		//for other months
		if (((endDateObj.getMonth()!=1) && (endDateObj.getMonth()!=2)) && (dto2.getYear()==(dto3.getYear()))) {
		
			if (dto2.getMonth()!=(dto3.getMonth()-1)) {
				dto2.setMonth(dto3.getMonth()-1);
				dto2.setPoints(0);
			}
			
			if (dto1.getMonth()!=(dto3.getMonth()-2)) {
				NewPointsDTO newObjDto = copiedList.stream().filter(n->(n.getMonth()==(dto3.getMonth()-2)) &&
																(n.getYear()==(dto3.getYear()))).findFirst().orElse(null);
	
				if (newObjDto!=null) {
					dto1.setMonth(newObjDto.getMonth());
					dto1.setPoints(newObjDto.getPoints());
				} else {
					dto1.setMonth(dto3.getMonth()-2);
					dto1.setPoints(0);
				}
				
			}
		}
		
		Map<Long, List<Map<String, Integer>>> result = new HashMap<>();

		List<Map<String, Integer>> pointsList = new ArrayList<>();
		int totalPoints = 0;
		for (NewPointsDTO dto : threeMonthsLst) {
			Map<String, Integer> map = new HashMap<>();
			String monthOfYear = Month.of(dto.getMonth()).getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);
			map.put(monthOfYear, dto.getPoints());
			pointsList.add(map);
			totalPoints+=dto.getPoints();
		}
		
		Map<String, Integer> mapTotal = new HashMap<>();
		mapTotal.put("Total", totalPoints);
		pointsList.add(mapTotal);
		result.put(customerId, pointsList);
		
		return result;
	}


	public List<NewPointsDTO> getDetailedRewardsNew(Long customerId) {
		List<RewardPoint> lstByCustId = rewardPointRepository.getDetailedRewardsByCustId(customerId);

		List<NewPointsDTO> newLstDto = new ArrayList<>();
		
		for(RewardPoint reward:lstByCustId) {
			ListIterator<NewPointsDTO> newItr = newLstDto.listIterator();
			
			boolean isYearPresent = containsYearNew(newLstDto,reward.getYear());
			
			if(isYearPresent) {
				 boolean isMonthPresent = containsMonthNew(newLstDto,reward.getMonth());
				 if (isMonthPresent) {
					 while(newItr.hasNext()) {
						 NewPointsDTO dto = newItr.next();
						 if (dto.getMonth()==reward.getMonth()) {
							 dto.setPoints(dto.getPoints()+reward.getPoints());
						}
					 } 	
				} else {
					NewPointsDTO newPointsDTO = new NewPointsDTO();
					newPointsDTO.setYear(reward.getYear());
					newPointsDTO.setMonth(reward.getMonth());
					newPointsDTO.setPoints(reward.getPoints());
					newLstDto.add(newPointsDTO);
				}
			}
			else {
				NewPointsDTO newPointsDTO = new NewPointsDTO();
				newPointsDTO.setYear(reward.getYear());
				newPointsDTO.setMonth(reward.getMonth());
				newPointsDTO.setPoints(reward.getPoints());
				newLstDto.add(newPointsDTO);
			}
		}
		
		return newLstDto;
		
	}
	
}
