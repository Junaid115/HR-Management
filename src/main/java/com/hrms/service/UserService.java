package com.hrms.service;

import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hrms.entity.User;
import com.hrms.exception.AlreadyExistsException;
import com.hrms.exception.InvalidPasswordException;
import com.hrms.exception.NotFoundException;
import com.hrms.repo.UserRepository;
import com.hrms.util.ApiResponse;
import com.hrms.util.PasswordValidator;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @Value("${file.upload-dir}")
	    private String uploadDir;

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	public ApiResponse create(User user, MultipartFile imageFile) {
	    // 1. Check if user already exists
	    User userByEmail = userRepo.findByEmail(user.getEmail());
	    if (userByEmail != null) {
	        throw new AlreadyExistsException("User already exists with email: " + user.getEmail());
	    }

//	    // 2. Validate password
	    if (!PasswordValidator.isValidPassword(user.getPassword())) {
	        throw new InvalidPasswordException("Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character.");
	    }

	    // 3. Save the image file
	    if (imageFile != null && !imageFile.isEmpty()) {
	        String imageName = storeFile(imageFile);
	        user.setImg(imageName);
	    }

	    // 4. Encode password
	    user.setPassword(passwordEncoder.encode(user.getPassword()));

	    // 5. Save user to DB
	    userRepo.save(user);

	    // 6. Return response
	    return ApiResponse.builder()
	            .status(true)
	            .message("User created successfully")
	            .build();
	}
	
	  public String storeFile(MultipartFile file) {
	        try {
	            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
	            Path path = Paths.get(uploadDir + fileName);
	            Files.createDirectories(path.getParent());
	            Files.write(path, file.getBytes());
	            return fileName;
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to store file: " + e.getMessage());
	        }
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
