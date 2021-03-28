package com.fuze.takehome.mybatis;

import javax.inject.Named;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

@Named
public interface UserDepartmentMapper {

	@Insert("INSERT INTO takeHome.user_department "
			+ "(user_id, department_id) "
			+ "VALUES "
			+ "(#{user}, #{department})")
	public int create(@Param("user") Long user, @Param("department") Long department);
}
