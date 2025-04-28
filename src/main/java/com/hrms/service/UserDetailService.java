package com.hrms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hrms.entity.User;
import com.hrms.repo.UserRepository;


@Service
public class UserDetailService implements UserDetailsService{
  
	@Autowired
	private  UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User byEmail = this.userRepo.findByEmail(email);
		System.out.println(byEmail);
		
		//loading user by user name
		return byEmail ;
	}

}
