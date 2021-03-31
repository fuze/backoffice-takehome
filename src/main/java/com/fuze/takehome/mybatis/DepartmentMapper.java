package com.fuze.takehome.mybatis;

import javax.inject.Named;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import com.fuze.takehome.model.Department;

@Named
public interface DepartmentMapper {

    @Insert("INSERT into takeHome.departments "
            + "(customer_id, name, description, active) "
            + "VALUES "
            + "(#{in.customerId}, #{in.name}, #{in.description}, #{in.active})")
    @Options(useGeneratedKeys = true, keyProperty = "in.id")
    public int create(@Param("in") Department in);

    @Select("SELECT "
            + "id, customer_id, name, description, active "
            + "FROM takeHome.departments "
            + "WHERE id = #{id} ")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "active", column = "active"),
    })
    public Department read(Long id);

    @Delete("DELETE FROM takeHome.departments WHERE id = #{id}")
    public int delete(Long id);
}

