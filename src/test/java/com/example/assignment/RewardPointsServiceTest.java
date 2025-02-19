package com.example.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.assignment.model.CustomerTransaction;
import com.example.assignment.model.RewardPoint;
import com.example.assignment.repositories.CustomerTransactionRepo;
import com.example.assignment.repositories.RewardPointRepo;
import com.example.assignment.services.MonthDTO;
import com.example.assignment.services.RewardPointsService;
import com.example.assignment.services.YearDTO;

@ExtendWith(MockitoExtension.class)
public class RewardPointsServiceTest {
	
	@InjectMocks
	RewardPointsService rewardPointsService;
	
	@Mock
	 private RewardPointRepo rewardPointRepository;
	 
	@Mock
	 private CustomerTransactionRepo customerTransactionRepo;
	
	
	@Test
	public void testgetTotalPointsByCustomer() {
		List<RewardPoint> lst = new ArrayList<>();
		when(rewardPointRepository.findByCustomerId(Mockito.any())).thenReturn(lst);
		int total = rewardPointsService.getTotalPointsByCustomer(Mockito.any());
		assertEquals(total, 0);
	}
	
	
	@Test
	public void testcalculateRewardPoints() {
		int total = rewardPointsService.calculateRewardPoints(0);
		assertEquals(total, 0);
	}
	
	
	@Test
	public void testgetMonthlyPoints() {
		List<CustomerTransaction> lstCustomerTransactions = new ArrayList<>();
		LocalDate startDate = LocalDate.of(2022,10, 1);
	    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		when(customerTransactionRepo.findTransactionsByCustomerIdAndDateForPoints((long)1, startDate, endDate)).thenReturn(lstCustomerTransactions);
		assertEquals(rewardPointsService.getMonthlyPoints((long)1,10,2022), 0);
	}
	
	
	@Test
	public void testgetMonthly() {
		List<RewardPoint> lstList = new ArrayList<>();
		RewardPoint rewardPoint = new RewardPoint();
		rewardPoint.setYear(2000);
		rewardPoint.setMonth(1);
		rewardPoint.setPoints(50);
		lstList.add(rewardPoint);

		RewardPoint rewardPoint1 = new RewardPoint();
		rewardPoint1.setYear(2000);
		rewardPoint1.setMonth(12);
		rewardPoint1.setPoints(150);
		lstList.add(rewardPoint1);
		
		when(rewardPointRepository.getMonthlyByCustId(Mockito.any())).thenReturn(lstList);
		List<YearDTO> yrList = new ArrayList<>();
		YearDTO yearDTO = new YearDTO();
		yearDTO.setYear(2000);
		MonthDTO monthDTO = new MonthDTO();
		monthDTO.setMonth(1);
		monthDTO.setPoints(50);
		List<MonthDTO> monLst = new ArrayList<>();
		monLst.add(monthDTO);
		yearDTO.setData(monLst);
		yrList.add(yearDTO);
		
		List<YearDTO> result = rewardPointsService.getMonthly((long) 1);
		assertEquals(result.get(0).getYear(), yrList.get(0).getYear());
		
	}
	
	
	@Test
	public void testcontainsYear() {
		List<YearDTO> list = new ArrayList<>();
		YearDTO yearDTO = new YearDTO();
		yearDTO.setYear(2000);
		list.add(yearDTO);
		
		boolean returned = rewardPointsService.containsYear(list, 2000);
		assertEquals(returned,true);
		
		
	}
	
	
	@Test
	public void testcontainsMonth() {
		List<MonthDTO> list = new ArrayList<>();
		MonthDTO monthDTO = new MonthDTO();
		monthDTO.setMonth(12);
		list.add(monthDTO);
		
		boolean returned = rewardPointsService.containsMonth(list, 12);
		assertEquals(returned,true);
		
		
	}
	
	
	

}
