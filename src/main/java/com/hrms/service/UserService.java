package com.hrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hrms.entity.User;
import com.hrms.exception.AlreadyExistsException;
import com.hrms.exception.InvalidPasswordException;
import com.hrms.exception.NotFoundException;
import com.hrms.repo.UserRepository;
import com.hrms.util.ApiResponse;
import com.hrms.util.PasswordValidator;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	public ApiResponse create(User user) {
		User userByEmail =userRepo.findByEmail(user.getEmail());
		if (userByEmail!=null) {
			throw new AlreadyExistsException("user aldready exists by given email"+user.getEmail());
		}
		
		User userByMobile = userRepo.findByMobileNo(user.getMobileNo());
		if(userByMobile !=null) {
			throw new AlreadyExistsException("user already exist by given Mobile number"+user.getMobileNo());
		}
		
		User userByAadhar =userRepo.findByAadharNo(user.getAadharNo());
		if(userByAadhar != null) {
			throw new AlreadyExistsException("user already exist by given Aadhar number"+ user.getAadharNo());
		}
		
		
		if (!PasswordValidator.isValidPassword(user.getPassword())) {
            throw new InvalidPasswordException("Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character.");
        }
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return ApiResponse.builder().status(true).message("user created successfully").build();
	}

	public ApiResponse update(User user) {
		userRepo.save(user);
		return ApiResponse.builder().status(true).message("user updated successfully").build();
	}

	public ApiResponse delete(Long id) {
		userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		userRepo.deleteById(id);
		return ApiResponse.builder().status(true).message("user deleted successfully").build();
	}

	public User findById(Long id) {
		 
	
		return  userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

}
