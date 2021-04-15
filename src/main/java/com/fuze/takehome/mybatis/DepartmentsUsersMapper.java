package com.fuze.takehome.mybatis;

import javax.inject.Named;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

// This class is used to insert relationships between departments and users (many-to-many) into the 'departments_users' join table   

@Named
public interface DepartmentsUsersMapper {

	@Insert("INSERT INTO takeHome.departments_users (department_id, user_id) "
			+ "VALUES (#{departmentId}, #{userId})")
	public int create(@Param("departmentId") Long departmentId, @Param("userId") Long userId);
}
