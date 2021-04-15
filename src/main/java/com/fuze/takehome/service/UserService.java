package com.fuze.takehome.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.DepartmentsUsersMapper;
import com.fuze.takehome.mybatis.UserMapper;

//Suggestion - Including JavaDoc, especially for the class, public fields, and public methods, will be
//helpful for other developers. 

public class UserService {

	@Inject
	private UserMapper userMapper;  // Change to private
	
	@Inject
	private DepartmentsUsersMapper duMapper; // use for assigning departments & users relationships
	
	@Inject
	private CustomerService customerService; // Change to private
	
	@Inject
	private DepartmentService departmentService; // Change to private

	@Transactional
	public User create(User user) 
	{
		// foreign-key validation and error handling: although foreign key constraint is already defined and enforced by the database,
		// however it will return a server error with stack trace in the API response when either the specified customerId or departmentIds
		// not found.  We will improve this by checking if the customerId and departmentIds already exist. If not, then throw
		// a 409 (Conflict) HTTP error with a user-friendly error message. This improves the user experience in using the API.
		
		// ensure the foreign key customer id exists, otherwise throws a 409 HTTP error (Conflict)
		if (!customerService.exists(user.getCustomerId())) {
			String message = "Can not create user because customerId '" + user.getCustomerId() + "' not found";
			throw new ClientErrorException(message, Response.status(Response.Status.CONFLICT).entity(message).build());
		}
		// ensure all department ids exist, otherwise throws a 409 error
		else if (user.getDepartmentIds() != null) {
			for (long departmentId: user.getDepartmentIds()) {
				if (!departmentService.exists(departmentId)) {
					String message = "Can not create user because department id '" + departmentId + "' not found";
					throw new ClientErrorException(message, Response.status(Response.Status.CONFLICT).entity(message).build());
				}
			}
		}
		
		// create the user in the DB
		userMapper.create(user);
		
		// create user-departments relationship
		user.getDepartmentIds().forEach(departmentId -> duMapper.create(departmentId, user.getId()));
		
		return user;		
	}

	// Set readOnly = true to let the transaction system knows that this is a read-only transaction
	@Transactional(readOnly = true)
	public User read(Long id) {
		User user = userMapper.read(id);
		if (user == null) {
			throw new NotFoundException();
		} 
		return user;
	}
	
	//Set readOnly = true to let the transaction system knows that this is a read-only transaction
	@Transactional(readOnly = true)
	public List<User> list() {
		LinkedList<User> userReturnList  = new LinkedList<User>();
		ArrayList<Long> userIds = new ArrayList<Long>(userMapper.list());
		userIds.forEach(id -> {
			// BUG FIXED: previously it was always returning the first user id in the for loop; 
			// this has been fixed.
			User user = userMapper.read(id);
			// check to see if the user still exists before adding
			if (user != null) {
				userReturnList.add(user);
			}
		});
		return userReturnList;
	}

	@Transactional
	public User delete(Long id) {
		User user = userMapper.read(id);
		if (user == null) {
			throw new NotFoundException();
		}
		int count = userMapper.delete(id);
		// It is considered bad practices to have an empty catch statement and it is unclear  
		// why it was included. The try...catch statement has been removed.
		if(count < 1)
		{
			throw new NotFoundException();	
		}
		return user;
	}	
	
	/**
	 * a helper method to see if the user exists
	 * 
	 * @param id - the user id
	 * @return true if the user exists, false otherwise.
	 */
	@Transactional
	public boolean exists(Long id) {
		return userMapper.read(id) != null;
	}
}
