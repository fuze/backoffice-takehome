package com.fuze.takehome.model;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

public class Department {

	private Long id;

	@NotNull(message = "customerId cannot be null")
	private Long customerId;

	@NotNull(message = "name cannot be null")
	private String name;

	private String description;

	@NotNull(message = "active cannot be null")
	private boolean active;
	
	@NotNull(message = "users cannot be null")
	private ArrayList<Long> userIds = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public ArrayList<Long> getUserIds() {
		return userIds;
	}
	
	public void setUserIds(ArrayList<Long> userIds) {
		this.userIds = userIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Department withId(Long id) {
		this.id = id;
		return this;
	}

	public Department withCustomerId(Long customerId) {
		this.customerId = customerId;
		return this;
	}
	
	public Department withUserIds(ArrayList<Long> userIds) {
		this.userIds = userIds;
		return this;
	}

	public Department withName(String name) {
		this.name = name;
		return this;
	}

	public Department withDescription(String description) {
		this.description = description;
		return this;
	}

	public Department withActive(boolean active) {
		this.active = active;
		return this;
	}
}
