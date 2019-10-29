package com.impelitAssignment.service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.impelitAssignment.service.impl.UserService;
import com.impelitAssignment.service.model.User;
import com.impelitAssignment.service.model.UserDTO;
import com.impelitAssignment.service.model.UserVerification;
import com.impelitAssignment.service.repository.RoleRepository;
import com.impelitAssignment.service.repository.UserRepository;
import com.impelitAssignment.service.repository.UserVerificationRepository;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserVerificationRepository userVerificationRepository;

	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO userDto,HttpServletRequest request) {
		
		try {
			User user = userService.registration(userDto);
			return ResponseEntity.ok(user);
		} catch(Exception ex) {
			return ResponseEntity.ok(ex.getMessage());
		}	
	}
	
	@GetMapping(value = "/public/verify/{token}")
	public ResponseEntity<?> verifyUserVerificationToken(@PathVariable(value = "token") String token) {
	     
		try {	    	 
		    UserVerification verificationToken = userService.getUserByverificationToken(token);		    
		    if (verificationToken == null) {
		    	return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		    }
		     
		    User user = verificationToken.getUser();		     
		    userRepository.save(user);
		    verificationToken.setVerified(true);
		    userVerificationRepository.save(verificationToken);
			return ResponseEntity.ok(HttpStatus.OK);			
	    } catch (Exception ex) {	    		    	 
	    	return new ResponseEntity<HttpStatus>(HttpStatus.EXPECTATION_FAILED);
	    }
	}
    @GetMapping("/current")
    public ResponseEntity<?> currentUser(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
            .stream()
            .map(a -> ((GrantedAuthority) a).getAuthority()).collect(Collectors.toList()));
        return ResponseEntity.ok(model);
    }

}
