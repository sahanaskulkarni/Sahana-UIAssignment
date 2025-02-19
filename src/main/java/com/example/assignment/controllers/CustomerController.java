package com.example.assignment.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.model.Customer;
import com.example.assignment.model.RegisterDTO;
import com.example.assignment.model.Roles;
import com.example.assignment.repositories.CustomerRepo;
import com.example.assignment.repositories.RolesRepo;
import com.example.assignment.services.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private RolesRepo rolesRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	 @Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO, HttpSession session){
		if(customerRepo.findByEmail(registerDTO.getEmail()).isPresent()) {
			return new ResponseEntity<String>("Name is taken",HttpStatus.BAD_REQUEST);
		}
		
		Customer customer = new Customer();
		customer.setName(registerDTO.getName());
		customer.setEmail(registerDTO.getEmail());
		customer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

		Roles roles = rolesRepo.findByName(registerDTO.getRole().toUpperCase());
		customer.setRoles(Collections.singletonList(roles));
		
		customerRepo.save(customer);
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(registerDTO.getEmail(), registerDTO.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());  
		
		return new ResponseEntity<>("User registered",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpSession session){
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
					);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());  
			 
			return new ResponseEntity<>("User signed in",HttpStatus.OK);
		}
		catch (Exception e) {
			
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
	}
	
	
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        response.setStatus(HttpServletResponse.SC_OK);
        
        return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
    }
	
	
	
	
	
	
}
