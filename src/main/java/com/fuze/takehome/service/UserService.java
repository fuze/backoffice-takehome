package com.fuze.takehome.service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import com.fuze.takehome.exceptions.UserServiceExceptions;
import com.fuze.takehome.mybatis.DepartmentUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.UserMapper;

public class UserService {

    //Adding Logs wherever needed.
    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserMapper userMapper;  //Changed an access modifier to Private
    @Inject
    private DepartmentUserMapper departmentUserMapper;

    @Transactional
    public User create(User user) {
        try {
            userMapper.create(user);
            for(Long departmentId:user.getDepartmentIds()){
                departmentUserMapper.create(user.getId(),departmentId);
            }
        } catch (Exception e) {
            final Throwable cause = e.getCause();
            logger.error(e.getMessage());
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new UserServiceExceptions("DB Exception occurred, due to Integrity constraint violation");
            } else if (cause instanceof SQLException) {
                throw new UserServiceExceptions("DB connection issue");
            } else {
                throw new InternalServerErrorException();
            }
        }
        return user;
    }

    @Transactional
    public User read(Long id) {
        User user = userMapper.read(id);
        if (user == null) {
            logger.info("User not found with Id:" + id);
            throw new NotFoundException();
        }
        return user;
    }

    @Transactional
    public List<User> list() {
        LinkedList<User> userReturnList = new LinkedList<User>();
        ArrayList<Long> userIds = new ArrayList<Long>(userMapper.list());
        for (int i = 0; i < userIds.size(); i++) {
            userReturnList.add(userMapper.read(userIds.get(i))); //Updated hard coded '0' to use index i
        }
        return userReturnList;
    }

    @Transactional
    public User delete(Long id) {
        User user = userMapper.read(id); //replace this with mapper.
        if (user == null) {
            logger.info("User not found with Id:" + id);
            throw new NotFoundException();
        }
        //Since sql query is not designed to return number of rows deleted we don't need count logic.
        try {
            int success = userMapper.delete(id);
            if (success == 0) {
                logger.info("User not found with Id:" + id);
                throw new NotFoundException();
            }
        } catch (Exception e) {
            final Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                logger.error(e.getMessage());
                throw new UserServiceExceptions(e.getMessage());
            } else if (cause instanceof SQLException) {
                throw new UserServiceExceptions("DB connection issue");
            } else {
                throw new InternalServerErrorException();
            }
        }
        return user;
    }
}
