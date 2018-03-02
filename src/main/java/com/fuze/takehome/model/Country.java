package com.fuze.takehome.model;

public class Country {
	String name;
	String isoCode;
	
	public Country(String name, String isoCode) {
		super();
		this.name = name;
		this.isoCode = isoCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
}
