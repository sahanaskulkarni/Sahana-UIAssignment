package com.example.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		 http

		 	.csrf(customizer->customizer.disable())
		 	
		 	.authorizeHttpRequests(authz -> authz
		            .requestMatchers("/customer/register","/customer/login","/customer/logout").permitAll()
		            .requestMatchers("/customer/home","/transactions/add","/transactions/delete","/transactions/update").hasAuthority("ADMIN")  
		            .requestMatchers("/customer/test","/transactions/getByCustomer","/rewardpoints/totalpoints",
		            		"/rewardpoints/detailedRewards","/rewardpoints/allRewards").hasAuthority("USER")
		            .anyRequest().authenticated()
		        )
		     .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		 
            
        
         return http.build();
        
    }
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
