package com.fuze.takehome.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.UserDepartment;
import com.fuze.takehome.mybatis.UserDepartmentMapper;

public class UserDepartmentService {

	@Inject
	public UserDepartmentMapper mapper;

	@Transactional
	public UserDepartment create(final UserDepartment entity) {
		mapper.create(entity);
		return entity;
	}

	@Transactional
	public UserDepartment read(final Long id) {
		UserDepartment userDepartment = mapper.read(id);
		if (userDepartment == null) {
			throw new NotFoundException();
		}
		return userDepartment;
	}

	@Transactional
	public List<UserDepartment> list() {
		return new ArrayList<UserDepartment>(mapper.list());
	}

	@Transactional
	public UserDepartment delete(final Long id) {
		UserDepartment userDepartment = this.read(id);
		if (userDepartment == null) {
			throw new NotFoundException();
		}
		int count = 0;
		count = mapper.delete(id);

		if (count < 1) {
			throw new NotFoundException();
		}
		return userDepartment;
	}
}
