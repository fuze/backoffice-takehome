/*
 * Updates by: Jay(Jatinder) Singh
 * Question 3- CustomerMapper
 */
package com.fuze.takehome.mybatis;

import javax.inject.Named;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fuze.takehome.model.Customer;

@Named
public interface CustomerMapper {
	
	@Insert("INSERT into takeHome.customers (name, active, contact_email, contact_firstname, contact_lastname) "
			+ "VALUES (#{in.name}, #{in.active}, #{in.contact.email}, #{in.contact.firstName}, #{in.contact.lastName})")
	@Options(useGeneratedKeys=true, keyProperty="in.id")
	public int create(@Param("in") Customer in);

	@Select("SELECT c.id,c.name,c.active,c.contact_email,c.contact_firstname,c.contact_lastname "
			+ "FROM takeHome.customers c "
			+ "WHERE c.id = #{id}")
	@Results(value = { 
			@Result(property = "id", 			column = "id"),
			@Result(property = "name", 			column = "name"),
			@Result(property = "active",		column = "active"),
			@Result(property = "contact.email",	column = "contact_email"),
			@Result(property = "contact.firstName",	column = "contact_firstname"),
			@Result(property = "contact.lastName",	column = "contact_lastname")
	})
	public Customer read(Long id); 	
	
	@Delete("DELETE FROM takeHome.customers WHERE id = #{id}")
	public int delete(Long id);
	
	@Update("UPDATE takeHome.customers SET"
			+ "name = #{in.name},"
			+  "active = #{in.active},"
			+  "contact.email = #{in.contact.email},"
			+  "contact.firstName = #{in.contact.firstName},"
			+  "contact.lastName = #{in.contact.lastName},"
			+  "WHERE id= #{id}")
	public int update(@Param("id") Long id, @Param("in") Customer in); 	
}