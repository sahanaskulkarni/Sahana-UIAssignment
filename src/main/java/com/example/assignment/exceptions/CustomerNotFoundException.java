package com.example.assignment.exceptions;

public class CustomerNotFoundException extends RuntimeException{
	public CustomerNotFoundException(String message) {
        super(message);
    }
}
