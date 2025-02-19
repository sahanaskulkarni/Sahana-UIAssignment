package com.example.assignment.exceptions;

public class AccessDeniedException extends RuntimeException{
	public AccessDeniedException(String message) {
        super(message);
    }
}
