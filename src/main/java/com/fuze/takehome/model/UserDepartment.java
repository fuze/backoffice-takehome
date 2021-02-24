package com.fuze.takehome.model;

import javax.validation.constraints.NotNull;

public class UserDepartment {

    @NotNull(message = "userId cannot be null")
    private Long userId;

    @NotNull(message = "departmentId cannot be null")
    private Long departmentId;

    public Long getUserId(){
        return userId;
    }

    public Long getDepartmentId(){
        return departmentId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public void setDepartmentId(Long departmentId){
        this.departmentId = departmentId;
    }

    public UserDepartment withUserId(Long userId){
        this.userId = userId;
        return this;
    }

    public UserDepartment withDepartmentId(Long departmentId){
        this.departmentId = departmentId;
        return this;
    }
}
