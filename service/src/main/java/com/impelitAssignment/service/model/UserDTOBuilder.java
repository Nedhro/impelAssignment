package com.impelitAssignment.service.model;

import java.util.ArrayList;
import java.util.List;

public class UserDTOBuilder {
	
private User user;
	
	public UserDTOBuilder withUser(User user) {
        this.user = user;
        return this;
    }
	
	public UserDTO build() {
		 UserDTO dto = new UserDTO();
		 if(user !=null) {
			 dto.setId(user.getId());
			 dto.setUsername(user.getUsername());
		 }
		 if(user.getRoles() != null && !user.getRoles().isEmpty()) {
			 List<String> roles = new ArrayList<>();
			 user.getRoles().forEach((Role role)->roles.add(role.getName()));
			 dto.setRoles(roles);
		 }
		 
		 return dto;
	 }

}
