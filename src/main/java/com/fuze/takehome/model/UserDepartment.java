package com.fuze.takehome.model;

import javax.validation.constraints.NotNull;

public class UserDepartment {
	
	@NotNull(message = "user_id cannot be null")
	private Long user_id;
	
	@NotNull(message = "department_id cannot be null")
	private Long department_id;
	
	public Long getUserId() {
		return user_id;
	}
	
	public Long getDepartmentId() {
		return department_id;
	}
	
	public void setUserId(Long user_id) {
		this.user_id = user_id;
	}
	
	public void setDepartmentId(Long department_id) {
		this.department_id = department_id;
	}
	
	public UserDepartment withUserId(Long user_id) {
		this.user_id = user_id;
		return this;
	}
	
	public UserDepartment withDepartmentId(Long department_id) {
		this.department_id = department_id;
		return this;
	}
}
