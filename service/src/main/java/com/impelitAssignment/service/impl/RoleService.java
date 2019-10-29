package com.impelitAssignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.impelitAssignment.service.model.Role;
import com.impelitAssignment.service.model.User;
import com.impelitAssignment.service.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public void addRole(Role role, User findOne) {

		roleRepository.save(role);
	}


}
