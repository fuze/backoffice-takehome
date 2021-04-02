package com.fuze.takehome.model;

import javax.validation.constraints.NotNull;

//New model for Department User relationship
public class DepartmentUser {
    @NotNull(message = "departmentId cannot be null")
    private Long departmentId;

    @NotNull(message = "userId cannot be null")
    private Long userId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setDepartmentId(Long departmentId){
        this.departmentId = departmentId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public DepartmentUser withUserId(Long userId){
        this.userId = userId;
        return this;
    }
    public DepartmentUser withDepartmentId(Long departmentId){
        this.departmentId = departmentId;
        return this;
    }
}
