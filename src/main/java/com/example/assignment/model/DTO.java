package com.example.assignment.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class DTO {

	private Long customerId; 
	private Long transactionId;	 
	private String transSpentDetails;	 
    private double transAmount;	 
    private String transDate;	 
    private int customerTotalPoints;
    private List<MPointsDTO> monthlyPointsData;

	public List<MPointsDTO> getMonthlyPointsData() {
		return monthlyPointsData;
	}
	public void setMonthlyPointsData(List<MPointsDTO> monthlyPointsData) {
		this.monthlyPointsData = monthlyPointsData;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransSpentDetails() {
		return transSpentDetails;
	}
	public void setTransSpentDetails(String transSpentDetails) {
		this.transSpentDetails = transSpentDetails;
	}
	public double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public int getCustomerTotalPoints() {
		return customerTotalPoints;
	}
	public void setCustomerTotalPoints(int customerTotalPoints) {
		this.customerTotalPoints = customerTotalPoints;
	}

	
}
