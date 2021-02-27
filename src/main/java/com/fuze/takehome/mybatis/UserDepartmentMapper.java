package com.fuze.takehome.mybatis;

import javax.inject.Named;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

@Named
public interface UserDepartmentMapper {
	
	@Insert("INSERT into takeHome.user_department "
			+ "(user_id, department_id) "
			+ "VALUES "
			+ "(#{userId}, #{departmentId})")
	public int create(@Param("userId") Long userId, @Param("departmentId") Long departmentId);
	
	@Delete("DELETE FROM takeHome.user_department WHERE user_id = #{userId}")
	public int deleteUser(Long userId);
	
	@Delete("DELETE FROM takeHome.user_department WHERE department_id = #{departmentId}")
	public int deleteDepartment(Long departmentId);
}

