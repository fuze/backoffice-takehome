package com.fuze.takehome.model;

public class Department implements OrgTreeItem {
	String name;
	String code;
	Company company;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public OrgTreeType getType() {
		return OrgTreeType.DEPARTMENT;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Department){
			Department aDept = (Department)obj;
			return (aDept.getCompany()!=null && aDept.getName()!=null &&
					aDept.getCompany().equals(this.getCompany()) &&
					aDept.getName().equals(this.getName()));
					
		}else{
			return false;
		}
	}
	
}
