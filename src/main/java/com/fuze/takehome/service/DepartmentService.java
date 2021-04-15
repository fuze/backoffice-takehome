package com.fuze.takehome.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fuze.takehome.model.Department;
import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.DepartmentMapper;
import com.fuze.takehome.mybatis.DepartmentsUsersMapper;

public class DepartmentService {

	private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
	//Keeps track of the first time a department name was created.
	//Note, use ConcurrentHashMap instead of HashMap for threat safety
	private static final Map<String, Date> existingDepartmentNames = new ConcurrentHashMap<String, Date>();
	
	@Inject
	protected DepartmentMapper departmentMapper;
	
	@Inject
	private DepartmentsUsersMapper duMapper;
	
	@Inject
	private UserService userService;
	

	@Transactional
	public Department create(Department department) {
		
		// ensure all userIds exist, otherwise throws a 409 error
		if (department.getUserIds() != null) {
			for (long userId : department.getUserIds()) {
				if (!userService.exists(userId)) {
					String message = "Can not create department because user id '" + userId + "' not found";
					throw new ClientErrorException(message,
							Response.status(Response.Status.CONFLICT).entity(message).build());
				}
			}
		}
		departmentMapper.create(department);
		
		// create department-users relationship
		department.getUserIds().forEach(userId -> duMapper.create(department.getId(), userId));
		
		//Department Name is not a unique field
		//However, print out a warning message to the log whenever 
		//we see a new department with a previously encountered name.
		//Not the most real-world scenario but serves the purposes. 
		if(department.getName() != null) {
			Date existingNameDate = existingDepartmentNames.get(department.getName());
			if(null != existingNameDate) {
				log.warn("Created a new Department with the previously used name '" 
						+ department.getName() + "'. Name first seen on " + dateFormat.format(existingNameDate));
			}
			else {
				existingDepartmentNames.put(department.getName(), new Date());
			}
		}
		
		return department;
	}
	
	@Transactional
	public Department read(Long id) {
		Department department = departmentMapper.read(id);
		if (department != null) {
			return department;
		} else {
			throw new NotFoundException();
		}
	}
	
	// List all departments
	@Transactional(readOnly = true)
	public List<Department> list() {
		LinkedList<Department> departmentReturnList  = new LinkedList<Department>();
		ArrayList<Long> departmentIds = new ArrayList<Long>(departmentMapper.list());
		departmentIds.forEach(id -> {
			Department department = this.read(id);
			if (department != null) {
				departmentReturnList.add(department);
			}
		});
		return departmentReturnList;
	}
	
	/**
	 * a helper method to see if the department exists
	 * 
	 * @param id - the department id
	 * @return true if the department exists, false otherwise.
	 */
	@Transactional
	public boolean exists(Long id) {
		return departmentMapper.read(id) != null;
	}

	@Transactional
	public Department delete(Long id) {
		throw new NotSupportedException();
	}
}
