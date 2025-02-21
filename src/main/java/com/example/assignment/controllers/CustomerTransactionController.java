package com.example.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.exceptions.TransactionException;
import com.example.assignment.model.DTO;
import com.example.assignment.services.CustomerTransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/transactions")
public class CustomerTransactionController {
	
		@Autowired
	    private CustomerTransactionService transactionService;
	   
		
		@GetMapping("/getByCustomer/{customerId}")
		@SecurityRequirement(name = "bearerAuth")
	    public ResponseEntity<List<DTO>> getTransactions(@PathVariable Long customerId) {
			try {
				List<DTO> lstCustomerTransactions = transactionService.getTransactionsByCustomer(customerId);			
		        return new ResponseEntity<List<DTO>> (lstCustomerTransactions,HttpStatus.OK);
			} catch (Exception e) {
				throw new TransactionException("Unable to fetch transactions");
			}
	    }


		@PostMapping("/add")
		@SecurityRequirement(name = "bearerAuth")
	    public ResponseEntity<DTO> addTransaction(@RequestBody DTO dto) {
			try {
				DTO resultDto = transactionService.addTransaction(dto);
		        return new ResponseEntity<DTO>(resultDto,HttpStatus.OK);
			} catch (Exception e) {
				throw new TransactionException("Addition of transaction failed");
			}
	    }
	

		 @DeleteMapping("/delete")
		 @SecurityRequirement(name = "bearerAuth")
		   public ResponseEntity<String> deleteTransaction(@RequestBody DTO dto) {
			 try {
		        transactionService.deleteTransaction(dto.getTransactionId());
		        return new ResponseEntity<String>("Transaction deleted successfully",HttpStatus.OK);
			 }catch (Exception e) {
				throw new TransactionException("Deletion of transaction failed");
			 }
		   }
		 
		 
		 @PutMapping("/update")
		 @SecurityRequirement(name = "bearerAuth")
		 public ResponseEntity<String> updateTransaction(@RequestBody DTO dto) {
			 try {
				 transactionService.updateTransaction(dto);
				 return new ResponseEntity<String>("updated",HttpStatus.OK);
			 }catch (Exception e) {
				 throw new TransactionException("Updation of transaction failed");
			 }
		 }
	 
}
