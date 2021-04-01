/*
 * Comments by: Jay(Jatinder) Singh
 * Question 1- UserService class feedback
 */
package com.fuze.takehome.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.UserMapper;

public class UserService {

	@Inject
	public UserMapper mapper;  // set internal class variables to private so there no changes directly made to the variable from other classes (only through available methods/functions)

	@Transactional
	public User create(User user) {
		mapper.create(user); // Check to see if the user already exists; if it doesn't then add the user other throw user does not exist exception. 
		//Check to see if any of the values are empty/NOT NULL and send corresponding error message
		//As we are connecting to a database, add try/Catch statements
		return user;		
	}

	@Transactional
	public User read(Long id) {
		User user = mapper.read(id);
		if (user != null) {
			return user;
		} else {
			throw new NotFoundException(); 
			// This a bad way to throw an exception; it's better to create another class which extends Exception and which better situated for this situation
			// For example, UserNotFoundException 
			// Please add try and catch statement. try for code to run and then catch exceptions and handle it accordingly . 
		}
	}
	
	@Transactional
	public List<User> list() {
		LinkedList<User> userReturnList  = new LinkedList<User>();
		ArrayList<Long> userIds = new ArrayList<Long>(mapper.list());
		for(int i = 0; i < userIds.size(); i++) {
			userReturnList.add(mapper.read(userIds.get(0))); 
			// this keeps adding the same user over and over again.
			// userReturnList.add(mapper.read(userIds.get(i))); update 0 to i so it goes through all the users.
		}
		return userReturnList;
	}

	@Transactional
	public User delete(Long id) {
		User user = this.read(id); //mapper.read(id);
		if (user  == null) {
			throw new NotFoundException();
			//As mentioned above for read method.
		}
		int count = 0;
		try {
			count = mapper.delete(id);
		}
		catch(Exception e){ 
			//First of all, create custom Exceptions to handle the situation
			// Second, print or store the error: 		e.printStackTrace();
		}
		if(count < 1)
		{
			throw new NotFoundException();	
			//As mentioned above for read method.
		}
		return user;
	}	
}
