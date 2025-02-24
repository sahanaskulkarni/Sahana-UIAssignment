package com.example.assignment.model;

public class AddTransDTO {
	
	private Long customerId; 
	private String transSpentDetails;	 
    private double transAmount;	  
    private String transDate;
    
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	
	
}
