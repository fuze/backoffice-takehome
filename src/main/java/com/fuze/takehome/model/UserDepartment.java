package com.fuze.takehome.model;

import javax.validation.constraints.NotNull;

public class UserDepartment {

	private Long id;

	@NotNull(message = "userId cannot be null")
	private Long userId;

	@NotNull(message = "departmentId cannot be null")
	private Long departmentId;

	public final Long getId() {
		return id;
	}

	public final void setId(final Long id) {
		this.id = id;
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(final Long userId) {
		this.userId = userId;
	}

	public final Long getDepartmentId() {
		return departmentId;
	}

	public final void setDepartmentId(final Long departmentId) {
		this.departmentId = departmentId;
	}

	public UserDepartment withId(final Long id) {
		this.id = id;
		return this;
	}

	public final UserDepartment withUserId(final Long userId) {
		this.userId = userId;
		return this;
	}

	public final UserDepartment withDepartmentId(final Long departmentId) {
		this.departmentId = departmentId;
		return this;
	}
}
