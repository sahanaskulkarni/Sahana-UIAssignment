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
import com.example.assignment.model.AddTransDTO;
import com.example.assignment.model.DTO;
import com.example.assignment.model.DeleteTransDTO;
import com.example.assignment.services.CustomerTransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/transactions")
@SecurityRequirement(name = "bearerAuth")
public class CustomerTransactionController {
	
		@Autowired
	    private CustomerTransactionService transactionService;
	   
		
		@GetMapping("/getByCustomer/{customerId}")
		@Operation(summary = "Get all transactions", description = "Fetches all transactions of a customer",
					responses= {
							@ApiResponse( description = "Success",responseCode = "200"),
							@ApiResponse(description = "Unauthorized",responseCode = "403")
						})
	    public ResponseEntity<List<DTO>> getTransactions(@PathVariable Long customerId) {
			try {
				List<DTO> lstCustomerTransactions = transactionService.getTransactionsByCustomer(customerId);			
		        return new ResponseEntity<List<DTO>> (lstCustomerTransactions,HttpStatus.OK);
			} catch (Exception e) {
				throw new TransactionException("Unable to fetch transactions");
			}
	    }

		
		@PostMapping("/add")
		@Operation(summary = "Addition of transaction", description = "Transaction is added and reward points are calculated",
						responses= {
							@ApiResponse( description = "Success",responseCode = "200"),
							@ApiResponse(description = "Unauthorized",responseCode = "403")
					})
	    public ResponseEntity<DTO> addTransaction(@RequestBody AddTransDTO dto) {
			try {
				DTO resultDto = transactionService.addTransaction(dto);
		        return new ResponseEntity<DTO>(resultDto,HttpStatus.OK);
			} catch (Exception e) {
				throw new TransactionException("Addition of transaction failed");
			}
	    }
		
	
		 @DeleteMapping("/delete")
		 @Operation(summary = "Deletion of transaction", description = "Transaction is deleted and corresponding reward points are deducted",
		 			responses= {
							@ApiResponse( description = "Success",responseCode = "200"),
							@ApiResponse(description = "Unauthorized",responseCode = "403")
						})
		   public ResponseEntity<String> deleteTransaction(@RequestBody DeleteTransDTO dto) {
			 try {
		        transactionService.deleteTransaction(dto.getTransactionId());
		        return new ResponseEntity<String>("Transaction deleted successfully",HttpStatus.OK);
			 }catch (Exception e) {
				throw new TransactionException("Deletion of transaction failed");
			 }
		   }
		 
		 
		 @PutMapping("/update")
		 @Operation(summary = "Updation of transaction", description = "Transaction is updated based on the fields passed to be updated and also corresponding reward points are updated",
		 					responses= {
									@ApiResponse( description = "Success",responseCode = "200"),
									@ApiResponse(description = "Unauthorized",responseCode = "403")
					})
		 public ResponseEntity<String> updateTransaction(@RequestBody DTO dto) {
			 try {
				 transactionService.updateTransaction(dto);
				 return new ResponseEntity<String>("updated",HttpStatus.OK);
			 }catch (Exception e) {
				 throw new TransactionException("Updation of transaction failed");
			 }
		 }
	 
}
