package com.fuze.takehome.service;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;

import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.UserDepartment;
import com.fuze.takehome.mybatis.UserDepartmentMapper;

public class UserDepartmentService {
	
	@Inject
	private UserDepartmentMapper mapper;

	@Transactional
	public UserDepartment create(UserDepartment userDepartment) {
		mapper.create(userDepartment);
		return userDepartment;		
	}

	@Transactional
	public UserDepartment readFromUser(Long id) {
		UserDepartment userDepartment = mapper.readFromUser(id);
		if (userDepartment != null) {
			return userDepartment;
		} else {
			throw new NotFoundException();
		}
	}
	
	@Transactional
	public UserDepartment readFromDepartment(Long id) {
		UserDepartment userDepartment = mapper.readFromDepartment(id);
		if (userDepartment != null) {
			return userDepartment;
		} else {
			throw new NotFoundException();
		}
	}

	@Transactional
	public UserDepartment delete(Long id) {
		throw new NotSupportedException();
	}
}
