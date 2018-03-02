package com.fuze.takehome.model;

public class Company implements OrgTreeItem{
	String name;
	String industry;
	Address headquarters;
	ContactInfo contactInfo;
	Integer numberOfEmployees;
	String domainName;
	
	public Company(String name, String industry, int numberOfEmployees,String domainName) {
		super();
		this.name = name;
		this.industry = industry;
		this.numberOfEmployees = numberOfEmployees;
		this.domainName = domainName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getHeadquarters() {
		return headquarters;
	}
	public void setHeadquarters(Address headquarters) {
		this.headquarters = headquarters;
	}
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	@Override
	public OrgTreeType getType() {
		return OrgTreeType.COMPANY;
	}

}

