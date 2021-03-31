package com.fuze.takehome.mybatis;

import javax.inject.Named;

import org.apache.ibatis.annotations.*;
import com.fuze.takehome.model.Customer;

@Named
public interface CustomerMapper {

    @Insert("INSERT into takeHome.customers (name, active, contact_email, contact_firstname, contact_lastname) "
            + "VALUES (#{in.name}, #{in.active}, #{in.contact.email}, #{in.contact.firstName}, #{in.contact.lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "in.id")
    public int create(@Param("in") Customer in);

    @Select("SELECT c.id,c.name,c.active,c.contact_email,c.contact_firstname,c.contact_lastname "
            + "FROM takeHome.customers c "
            + "WHERE c.id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "active", column = "active"),
            @Result(property = "contact.email", column = "contact_email"),
            @Result(property = "contact.firstName", column = "contact_firstname"),
            @Result(property = "contact.lastName", column = "contact_lastname")
    })
    public Customer read(Long id);

    @Update("UPDATE takeHome.customers c SET " +
            "c.name = #{in.name}" +
            "c.active = #{in.active}" +
            "c.contact_email = #{in.contact.email}" +
            "c.contact_firstname = #{in.contact.firstName}" +
            "c.contact_lastname = #{in.contact.lastName}" +
            "WHERE c.id = #{id}")
    public int update(@Param("id") Long id, @Param("in") Customer in);

    @Delete("DELETE FROM takeHome.customers WHERE id = #{id}")
    public int delete(Long id);
}