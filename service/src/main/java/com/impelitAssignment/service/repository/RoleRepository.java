package com.impelitAssignment.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impelitAssignment.service.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

   Role findByName(String username);
	
	@SuppressWarnings("unchecked")
	Role save(Role role);

}
