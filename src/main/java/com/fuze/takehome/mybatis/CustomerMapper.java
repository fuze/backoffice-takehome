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
			+ "VALUES (#{c.name}, #{c.active}, #{c.contact.email}, #{c.contact.firstName}, #{c.contact.lastName})")
	@Options(useGeneratedKeys=true, keyProperty="c.id")
	public int create(@Param("c") Customer c);
	
	// Supports both full and partial update
	@Update({
		"<script>",
		"UPDATE takeHome.customers",
		"<set>",
		"<if test ='c.contact.email != null'>contact_email=#{c.contact.email},</if>",
		"<if test ='c.contact.firstName != null'>contact_firstname=#{c.contact.firstName},</if>",
		"<if test ='c.contact.lastName != null'>contact_lastname=#{c.contact.lastName},</if>",
		"name=#{c.name}, active=#{c.active}",
		"</set>",
		"where id=#{c.id}",
		"</script>"
	})
	public int update(@Param("c") Customer c);

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
}