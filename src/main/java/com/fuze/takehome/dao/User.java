package com.fuze.takehome.dao;

public class User {
	private final Long id;
	private final String firstName;
	private final String lastName;
	private final Long locationId;
	
	User(Long id, String firstName, String lastName, Long locationId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.locationId = locationId;
	}
	
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Long getLocationId() {
		return locationId;
	}
	
}
