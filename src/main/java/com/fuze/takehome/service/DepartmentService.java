package com.fuze.takehome.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fuze.takehome.model.Department;
import com.fuze.takehome.mybatis.DepartmentMapper;
import com.fuze.takehome.mybatis.UserDepartmentMapper;

public class DepartmentService {

	private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	// Keeps track of the first time a department name was created
	private static final Map<String, Date> existingDepartmentNames = new ConcurrentHashMap<String, Date>(); // ConcurrentHashMap for thread safety

	@Inject
	private DepartmentMapper mapper;

	@Inject
	private UserDepartmentMapper udMapper;

	@Transactional
	public Department create(Department department) {
		mapper.create(department);
		department.getUsers().forEach(userId -> udMapper.create(userId, department.getId()));
		// Department Name is not a unique field
		// However, print out a warning message to the log whenever
		// we see a new department with a previously encountered name.
		// Not the most real-world scenario but serves the purposes.

		if (department.getName() != null) {
			Date existingNameDate = existingDepartmentNames.get(department.getName());
			if (null != existingNameDate) {
				log.warn(String.format("Created a new Department with the previously used name '%s'. Name first seen on %s",
						department.getName(), dateFormat.format(existingNameDate)));
			} else {
				existingDepartmentNames.put(department.getName(), new Date());
			}
		}

		return department;
	}

	@Transactional
	public Department read(Long id) {
		Department department = mapper.read(id);
		if (department == null) {
			throw new NotFoundException();
		}

		return department;
	}

	@Transactional
	public Department delete(Long id) {
		throw new NotSupportedException();
	}
}
