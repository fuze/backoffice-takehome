package com.fuze.takehome.dao;

public class Location {
	private final Long id;
	private final String name;
	
	public Location(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
