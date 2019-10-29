package com.impelitAssignment.service.model;

public class RoleDTOBuilder {
	private Role role;
	
	public RoleDTOBuilder withRole(Role role) {
        this.role = role;
        return this;
    }
	

	public RoleDTO buildOnlyRole() {
		 RoleDTO dto = new RoleDTO();
		 if(role !=null) {
			 dto.setId(role.getId());
			 dto.setName(role.getName());
		 }		 
		 return dto;
	 }


}
