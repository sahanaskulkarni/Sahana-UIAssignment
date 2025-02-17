package com.example.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.model.CustomerTransaction;
import com.example.assignment.services.CustomerTransactionService;
import com.example.assignment.services.DTO;

@RestController
@RequestMapping("/transactions")
public class CustomerTransactionController {
	
		@Autowired
	    private CustomerTransactionService transactionService;
	   

		@GetMapping("/getByCustomer")
	    public List<CustomerTransaction> getTransactions(@RequestBody DTO dto) {
	        return transactionService.getTransactionsByCustomer(dto.getCustomerId());
	    }


		@PostMapping("/add")
	    public DTO addTransaction(@RequestBody DTO dto) {
	        return transactionService.addTransaction(dto);
	    }
		

		 @DeleteMapping("/delete")
		   public String deleteTransaction(@RequestBody DTO dto) {
		        transactionService.deleteTransaction(dto.getTransactionId());
		        return "Transaction deleted successfully";
		   }
		 
		 @PutMapping("/update")
		 public String updateTransaction(@RequestBody DTO dto) {
			 transactionService.updateTransaction(dto);
			 return "updated";
		 }
	 
}
