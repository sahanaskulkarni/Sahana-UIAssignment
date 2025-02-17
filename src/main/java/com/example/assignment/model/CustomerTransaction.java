package com.example.assignment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CustomerTransaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String spentdetails;
    private double amount;
    private String date;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    
	public Long getId() {
		return id;
	}

	public String getSpentdetails() {
		return spentdetails;
	}


	public void setSpentdetails(String spentdetails) {
		this.spentdetails = spentdetails;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}





	@Override
	public String toString() {
		return "CustomerTransactionEntity [id=" + id + ", spentdetails=" + spentdetails + ", amount=" + amount
				+ ", customer=" + customer + "]";
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


}
