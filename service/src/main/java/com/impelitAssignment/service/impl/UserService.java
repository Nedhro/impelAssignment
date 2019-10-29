package com.impelitAssignment.service.impl;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.impelitAssignment.service.model.User;
import com.impelitAssignment.service.model.UserDTO;
import com.impelitAssignment.service.model.UserVerification;
import com.impelitAssignment.service.repository.UserRepository;
import com.impelitAssignment.service.repository.UserVerificationRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserVerificationRepository userVerificationRepository;
	
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Transactional
	public User registration(UserDTO userDto) throws Exception {
		
		if (this.findByUserName(userDto.getUsername()) != null) {
            throw new Exception("User already exist with this username "+userDto.getUsername());
        }	
		User user = new User();            
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername().trim());
        ArrayList<String> roles=new ArrayList<String>();
        roles.add("user");
        return userRepository.save(user);
	}

	public void generateVerificationToken(User user, String token) {
		
		UserVerification verifyToken = new UserVerification(token, user);		
		userVerificationRepository.save(verifyToken);
		
	}

	public UserVerification getUserByverificationToken(String token) {
		return userVerificationRepository.findByTokenAndVerified(token,false);
	}
}
