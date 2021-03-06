package com.recipes.jwt.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.recipes.jwt.entity.User;
import com.recipes.jwt.repository.UserRepository;

@Service
public class CustomUserDetailService  implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	/*Spring Security will load User details to perform authentication & authorization.
	 *  So it has UserDetailsService interface that we need to implement.
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = repository.findByUserName(username);
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
		
	}

}
