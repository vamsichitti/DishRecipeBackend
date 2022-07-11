package com.recipes.jwt.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.recipes.controller.RecipeBackendController;
import com.recipes.exception.UserAlreadyRegisteredException;
import com.recipes.jwt.entity.AuthRequest;
import com.recipes.jwt.entity.User;
import com.recipes.jwt.repository.UserRepository;
import com.recipes.jwt.service.CustomUserDetailService;
import com.recipes.jwt.util.JwtUtil;

@RestController
public class JwtController {
	Logger logger = LoggerFactory.getLogger(JwtController.class); 
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CustomUserDetailService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authrequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authrequest.getUserName(), authrequest.getPassword()));
		} catch (Exception e) {
			logger.info("USERNAME and PASSWORD are InValid");
			throw new Exception("Invalid username and password");
		}
		logger.info("USERNAME and PASSWORD are Valid");
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authrequest.getUserName());

		return jwtUtil.generateToken(userDetails);
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@RequestBody User user) throws UserAlreadyRegisteredException{
		Optional<?> userId = userRepo.findById(user.getId());
		if(userId.isPresent()) {
			throw new UserAlreadyRegisteredException("User with ID "+user.getId()+" is already Registered");
			
		}
		else {
			userRepo.save(user);
			logger.info("a new user with name {} registered Successfully",user.getUserName());
			return new ResponseEntity<>("You are Successfully Registered",HttpStatus.CREATED);
		}
	}

}
