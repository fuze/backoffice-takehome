package com.fuze.takehome.service;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;

import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.Department;
import com.fuze.takehome.mybatis.DepartmentMapper;

public class DepartmentService {

	@Inject
	protected DepartmentMapper mapper;

	@Transactional
	public Department create(Department department) {
		mapper.create(department);
		return department;
	}
	
	@Transactional
	public Department read(Long id) {
		Department department = mapper.read(id);
		if (department != null) {
			return department;
		} else {
			throw new NotFoundException();
		}
	}

	@Transactional
	public Department delete(Long id) {
		throw new NotSupportedException();
	}
}
