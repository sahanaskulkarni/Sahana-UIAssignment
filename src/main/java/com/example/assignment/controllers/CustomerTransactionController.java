package com.example.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.model.CustomerTransaction;
import com.example.assignment.model.DTO;
import com.example.assignment.services.CustomerTransactionService;

@RestController
@RequestMapping("/transactions")
public class CustomerTransactionController {
	
		@Autowired
	    private CustomerTransactionService transactionService;
	   

		@GetMapping("/getByCustomer")
	    public ResponseEntity<List<DTO>> getTransactions(@RequestBody DTO dto) {
			List<DTO> lstCustomerTransactions = transactionService.getTransactionsByCustomer(dto.getCustomerId());
			
	        return new ResponseEntity<List<DTO>> (lstCustomerTransactions,HttpStatus.OK);
	    }


		@PostMapping("/add")
	    public ResponseEntity<DTO> addTransaction(@RequestBody DTO dto) {
			DTO resultDto = transactionService.addTransaction(dto);
	        return new ResponseEntity<DTO>(resultDto,HttpStatus.OK);
	    }
	

		 @DeleteMapping("/delete")
		   public ResponseEntity<String> deleteTransaction(@RequestBody DTO dto) {
		        transactionService.deleteTransaction(dto.getTransactionId());
		        return new ResponseEntity<String>("Transaction deleted successfully",HttpStatus.OK);
		   }
		 
		 @PutMapping("/update")
		 public ResponseEntity<String> updateTransaction(@RequestBody DTO dto) {
			 transactionService.updateTransaction(dto);
			 return new ResponseEntity<String>("updated",HttpStatus.OK);
		 }
	 
}
