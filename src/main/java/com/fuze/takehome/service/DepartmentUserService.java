package com.fuze.takehome.service;

import com.fuze.takehome.exceptions.UserServiceExceptions;
import com.fuze.takehome.model.DepartmentUser;
import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.DepartmentMapper;
import com.fuze.takehome.mybatis.DepartmentUserMapper;
import com.fuze.takehome.mybatis.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class DepartmentUserService {
    static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    private UserMapper userMapper;

    @Inject
    private DepartmentUserMapper departmentUserMapper;

    @Inject
    private DepartmentMapper departmentMapper;

    @Transactional
    public DepartmentUser create(DepartmentUser departmentUser) {

        try {
            if(departmentMapper.read(departmentUser.getDepartmentId())!=null && userMapper.read(departmentUser.getUserId())!=null){
                departmentUserMapper.create(departmentUser.getUserId(),departmentUser.getDepartmentId());
            }
            else{
                throw new NotFoundException();
            }

        }
        catch(UserServiceExceptions e){
            throw new UserServiceExceptions(e.getMessage());
        }
        catch (Exception e) {
            final Throwable cause = e.getCause();
            logger.error(e.getMessage());
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new UserServiceExceptions("DB Exception occurred, due to Integrity constraint violation");
            } else if (cause instanceof SQLException) {
                throw new UserServiceExceptions("DB connection issue");
            }else {
                throw new InternalServerErrorException();
            }
        }
        return departmentUser;
    }
}
