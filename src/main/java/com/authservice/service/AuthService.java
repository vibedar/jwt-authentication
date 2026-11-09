package com.authservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.entity.User;
import com.authservice.payload.APIResponse;
import com.authservice.payload.UserDto;
import com.authservice.repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public APIResponse<String> register(UserDto dto){
		
		//API Response object
		APIResponse<String> response=new APIResponse<>();
		if(repo.existsByUsername(dto.getUsername())) {
			response.setMessage("Registration failed");
			response.setStatus(500);
			response.setDate("User with username already exists");
		}
		
		//checkby whether email exists
		if(repo.existsByEmail(dto.getEmail())) {
			response.setMessage("Registration failed");
			response.setStatus(500);
			response.setDate("User with email already exists");
			return response;
		}
		
		//Encode the password before saving that to the database
		String encryptedPassword=passwordEncoder.encode(dto.getPassword());
		User user=new User();
		BeanUtils.copyProperties(dto, user);
		user.setPassword(encryptedPassword);
		user.setRole("ROLE_USER");
		User savedUser=repo.save(user);
		
		if(savedUser==null) {
			//CustomException
		}
		response.setMessage("Registration Completed");
		response.setStatus(500);
		response.setDate("User has been registered");
		return response;
		
		
	}
}
