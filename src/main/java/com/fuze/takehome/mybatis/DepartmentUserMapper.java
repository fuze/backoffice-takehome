package com.fuze.takehome.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface DepartmentUserMapper {
    @Insert("INSERT INTO takeHome.department_user " +
            "(user_id,department_id) " +
            "VALUES " +
            "(#{u_id}, #{d_id})")
    public int create(@Param("u_id") Long u_id,@Param("d_id") Long d_id);


}
