package com.fuze.takehome.mybatis;


import com.fuze.takehome.model.UserDepartment;
import org.apache.ibatis.annotations.*;

import javax.inject.Named;

@Named
public interface UserDepartmentMapper {

    @Insert("INSERT into takeHome.user_departments " +
            "(user_id, department_id) " +
            "VALUES " +
            "(#{in.userId}, #{in.departmentId})")
    @Options(useGeneratedKeys=true)
    public int create(@Param("in") UserDepartment in);

    @Select("SELECT user_id, department_id " +
            "FROM takeHome.user_departments " +
            "WHERE user_id = #{id}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "departmentId", column = "department_id")
    })
    public UserDepartment readFromUser(Long id);

    @Select("SELECT user_id, department_id " +
            "FROM takeHome.user_departments " +
            "WHERE department_id = #{id}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "departmentId", column = "department_id")
    })
    public UserDepartment readFromDepartment(Long id);

    @Delete("DELETE FROM takeHome.user_departments WHERE user_id = #{id}")
    public int deleteFromUser(Long id);

}
