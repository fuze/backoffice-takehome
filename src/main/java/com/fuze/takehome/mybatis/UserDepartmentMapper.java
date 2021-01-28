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

	@Insert("INSERT into takeHome.user_department " + "(user_id, department_id) " + "VALUES "
			+ "(#{in.userId}, #{in.departmentId})")
	@Options(useGeneratedKeys = true)
	public int create(@Param("in") final UserDepartment in);

	@Select("SELECT id, user_id, department_id FROM takeHome.user_department " + "WHERE id = #{id}")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "userId", column = "user_id"),
			@Result(property = "departmentId", column = "department_id") })
	public UserDepartment read(final Long id);

	@Select("SELECT id, user_id, department_id FROM takeHome.user_department")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "userId", column = "user_id"),
			@Result(property = "departmentId", column = "department_id") })
	public Collection<UserDepartment> list();

	@Delete("DELETE FROM takeHome.user_department WHERE id = #{id}")
	public int delete(final Long id);
}
