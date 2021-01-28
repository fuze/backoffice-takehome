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

public class DepartmentService {

	private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	/*
	 * This one took me a moment to find. I've spent a lot of time the last few
	 * weeks building APIs with AWS lambdas. They have clearly spoiled me into
	 * forgetting that multi-threaded applications exist...
	 * 
	 * On another note, I object to the log warning in the create function. WHEN the
	 * application gets restarted or is forced to scale (assuming it would ever need
	 * to scale horizontally), it would lose all track of duplicates. That map needs
	 * to be periodically materialized or at the very lease re-initialized at
	 * startup.
	 */

	// Keeps track of the first time a department name was created
	private static final Map<String, Date> existingDepartmentNames = new ConcurrentHashMap<String, Date>();

	@Inject
	protected DepartmentMapper mapper;

	@Transactional
	public Department create(Department department) {
		mapper.create(department);

		// Department Name is not a unique field
		// However, print out a warning message to the log whenever
		// we see a new department with a previously encountered name.
		// Not the most real-world scenario but serves the purposes.
		if (department.getName() != null) {
			Date existingNameDate = existingDepartmentNames.get(department.getName());
			if (null != existingNameDate) {
				log.warn("Created a new Department with the previously used name '" + department.getName()
						+ "'. Name first seen on " + dateFormat.format(existingNameDate));
			} else {
				existingDepartmentNames.put(department.getName(), new Date());
			}
		}

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
