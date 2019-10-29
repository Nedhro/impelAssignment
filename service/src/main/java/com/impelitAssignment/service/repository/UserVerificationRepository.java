package com.impelitAssignment.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.impelitAssignment.service.model.User;
import com.impelitAssignment.service.model.UserVerification;

public interface UserVerificationRepository  extends JpaRepository<UserVerification, Long>{

	UserVerification findByTokenAndVerified(String token,boolean verified);

	UserVerification findByUser(User user);
	
}
