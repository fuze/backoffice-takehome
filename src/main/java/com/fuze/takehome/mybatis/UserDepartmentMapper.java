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
import com.fuze.takehome.model.UserDepartment;

@Named
public interface UserDepartmentMapper {
		
	@Insert("INSERT into takeHome.users_departments "
			+ "(user_id, department_id) "
			+ "VALUES "
			+ "(#{in.user_id}, #{in.department_id})")
	@Options(keyProperty="(in.user_id, in.department_id)")
	public int create(@Param("in") UserDepartment in);

	@Select("SELECT "
			+ "user_id, department_id "
			+ "FROM takeHome.users_departments "
			+ "WHERE user_id = #{id}" )
	@Results(value = { 
			@Result(property = "userId",		column = "user_id"),
			@Result(property = "departmentId", 	column = "department_id")
	})
	public UserDepartment readFromUser(Long id);
	
	@Select("SELECT "
			+ "user_id, department_id "
			+ "FROM takeHome.users_departments "
			+ "WHERE department_id = #{id}" )
	@Results(value = { 
			@Result(property = "userId",		column = "user_id"),
			@Result(property = "departmentId", 	column = "department_id")
	})
	public UserDepartment readFromDepartment(Long id);
	
	@Delete("DELETE FROM takeHome.user_departments WHERE user_id = #{id}")
	public int deleteFromUser(Long id); 	
	
	@Delete("DELETE FROM takeHome.user_departments WHERE department_id = #{id}")
	public int deleteFromDepartment(Long id); 
}

