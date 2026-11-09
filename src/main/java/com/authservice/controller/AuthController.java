package com.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.authservice.payload.APIResponse;
import com.authservice.payload.LoginDto;
import com.authservice.payload.UserDto;
import com.authservice.service.AuthService;
import com.authservice.service.JwtService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private AuthService service;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/register")
	public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto dto){
		APIResponse<String> response=service.register(dto);
		return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponse<String>> login(@RequestBody LoginDto logdto){
		
		APIResponse<String> response = new APIResponse();
		
		UsernamePasswordAuthenticationToken token = new 
				UsernamePasswordAuthenticationToken(logdto.getUsername(),logdto.getPassword());
		try {
			 Authentication authenticate = authManager.authenticate(token);
			 
			 if(authenticate.isAuthenticated()) {
				 String jwtToken = jwtService.generateToken(logdto.getUsername(),
			                authenticate.getAuthorities().iterator().next().getAuthority());

			            response.setMessage("Login Successful");
			            response.setStatus(200);
			            response.setDate(jwtToken);  // return JWT
			            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 response.setMessage("Failed");
		 response.setStatus(401);
		 response.setDate("Un-Authorized Access");
		 return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
	 }
	
	@PostMapping("/message")
	public String getMessage() {
		return "welcome hottu";
	}

			

}
