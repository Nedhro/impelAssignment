package com.impelitAssignment.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.impelitAssignment.service.model.Role;
import com.impelitAssignment.service.model.RoleDTO;
import com.impelitAssignment.service.repository.RoleRepository;

@RestController
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PostMapping("/role")
	public ResponseEntity<?> save(@RequestBody RoleDTO roleDto) {
		Role role = roleRepository.findByName(roleDto.getName());
		if(role != null) return new ResponseEntity<String>("Error! Role already Exist with this name",HttpStatus.FOUND);
    	role = new Role();
    	role.setName(roleDto.getName());
        roleRepository.save(role);        
		return ResponseEntity.ok(role);
	}

}
