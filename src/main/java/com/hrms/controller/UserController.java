package com.hrms.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.entity.User;
import com.hrms.service.UserService;
import com.hrms.util.ApiResponse;



@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	 
	@PostMapping("/create")
	private ResponseEntity<ApiResponse> create(@RequestBody User user){
		System.out.println(user);
		return new ResponseEntity<ApiResponse>(userService.create(user),HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	private ResponseEntity<ApiResponse> update(@RequestBody User user){
		return new ResponseEntity<ApiResponse>(userService.update(user),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id){
		return new ResponseEntity<ApiResponse>(userService.delete(id),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	private User findById(@PathVariable("id") Long id) {
		return userService.findById(id);
	}
	
	@GetMapping("/all")
	private ResponseEntity<List<User>> findAll() {
	    List<User> users = userService.findAll()
	    		.stream()
	    		.filter(user -> user.getRoles().stream()
	    				.map(String::toLowerCase)
	    				.anyMatch(role -> role.equals("user")))
	    				.collect(Collectors.toList());
	    return new ResponseEntity<>(users, HttpStatus.OK);
	}

	

}
