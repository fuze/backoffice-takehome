package com.fuze.takehome.mybatis;

import javax.inject.Named;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

@Named
public interface DepartmentUserMapper {

	@Insert("INSERT into takehome.departmentuserrelation "
			+ "(user_id, department_id) "
			+ "VALUES "
			+ "(#{userId}, #{departmentId})")
	public int createDeptUser(@Param("userId") Long userId, @Param("departmentId") Long departmentIdserId);
	
	@Delete("DELETE from takehome.departmentuserrelation WHERE department_id = #{departmentId}")
	public void deleteDepartment(Long departmentId);
	
	@Delete("DELETE from takehome.departmentuserrelation WHERE user_id = #{userId}")
	public void deleteUser(Long userId);
	
}
