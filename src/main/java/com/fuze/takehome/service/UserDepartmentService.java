package com.fuze.takehome.service;

import com.fuze.takehome.model.UserDepartment;
import com.fuze.takehome.mybatis.UserDepartmentMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

public class UserDepartmentService {

    @Inject
    public UserDepartmentMapper mapper;

    @Transactional
    public UserDepartment create(UserDepartment userDepartment){
        mapper.create(userDepartment);
        return userDepartment;
    }

    @Transactional
    public UserDepartment readFromUser(Long id){
        UserDepartment userDepartment = mapper.readFromUser(id);
        if(userDepartment != null){
            return userDepartment;
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional
    public UserDepartment readFromDepartment(Long id){
        UserDepartment userDepartment = mapper.readFromDepartment(id);
        if(userDepartment != null){
            return userDepartment;
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional
    public UserDepartment deleteFromUser(Long id){
        UserDepartment userDepartment = mapper.readFromUser(id);
        if(userDepartment != null){
            mapper.deleteFromUser(id);
            return userDepartment;
        } else{
            throw new NotFoundException();
        }
    }

}
