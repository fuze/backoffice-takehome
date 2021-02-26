package com.fuze.takehome.mybatis;

import java.util.Collection;

import javax.inject.Named;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fuze.takehome.model.User;

@Named
public interface UserMapper {
		
	@Insert("INSERT into takeHome.users "
			+ "(customer_id, username, first_name, last_name, email, telephone_number, mobile_number, fax_number, department_id, active) "
			+ "VALUES "
			+ "(#{in.customerId}, #{in.userName}, #{in.firstName}, #{in.lastName}, #{in.email}, #{in.telephoneNumber}, #{in.mobileNumber}, #{in.faxNumber}, #{in.departmentId}, #{in.active})")
	@Options(useGeneratedKeys=true, keyProperty="in.id")
	public int create(@Param("in") User in);

	@Select("SELECT "
			+ "id, customer_id, username, first_name, last_name, email, telephone_number, mobile_number, fax_number, department_id, active "
			+ "FROM takeHome.users "
			+ "WHERE id = #{id}" )
	@Results(value = { 
			@Result(property = "id", 			column = "id"),
			@Result(property = "customerId", 	column = "customer_id"), 
			@Result(property = "userName", 		column = "username"),
			@Result(property = "firstName", 	column = "first_name"),
			@Result(property = "lastName", 		column = "last_name"),
			@Result(property = "email", 		column = "email"),
			@Result(property = "telephoneNumber", 		column = "telephone_number"),
			@Result(property = "mobileNumber", 		column = "mobile_number"),
			@Result(property = "faxNumber", 		column = "fax_number"),
			@Result(property = "departmentId", 	column = "department_id"),
			@Result(property = "active", 		column = "active")
	})
	public User read(Long id);
	
	@Select("SELECT id FROM takeHome.users")
	public Collection<Long> list(); 	
	
	@Update("UPDATE	takeHome.users "
			+ "SET customer_id = #{in.customerId}, "
			+ "username = #{in.userName}, "
			+ "first_name = #{in.firstName}, "
			+ "last_name = #{in.lastName}, "
			+ "email = #{in.email}, "
			+ "telephone_number = #{in.telephoneNumber}, "
			+ "mobile_number = #{in.mobileNumber}, "
			+ "fax_number = #{in.faxNumber}, "
			+ "department_id = #{in.departmentId}, "
			+ "active = #{in.active} "
			+ "WHERE id = #{id}")
	public int update(@Param("id") Long id, @Param("in") User in);
	
	@Delete("DELETE FROM takeHome.users WHERE id = #{id}")
	public int delete(Long id); 	
}

