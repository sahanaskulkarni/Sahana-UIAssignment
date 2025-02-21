package com.example.assignment;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.assignment.exceptions.AccessDeniedException;
import com.example.assignment.exceptions.CustomerNotFoundException;
import com.example.assignment.exceptions.RewardPointsException;
import com.example.assignment.exceptions.TransactionException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler(AccessDeniedException.class)
	 public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(TransactionException.class)
	 public ResponseEntity<String> handleTransactionException(TransactionException ex) {
       return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RewardPointsException.class)
	 public ResponseEntity<String> handleRewardsException(RewardPointsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
			
		
}
