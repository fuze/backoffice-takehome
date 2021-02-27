package com.fuze.takehome.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.UserDepartmentMapper;
import com.fuze.takehome.mybatis.UserMapper;

public class UserService {

	@Inject
	// Mapper should not be public
	protected UserMapper mapper;
	
	@Inject
	protected UserDepartmentMapper userDepartmentMapper;

	@Transactional
	public User create(User user) {
		
		//Handle error cases when mapper.create() is unable to create a user or when user is not defined correctly
		try {
			mapper.create(user);
			for (Long departmentId : user.getDepartmentIds()) {
				userDepartmentMapper.create(user.getId(), departmentId);
			}
			return user;
		}
		//Handle different cases
		catch (DataIntegrityViolationException e) {
			throw new BadRequestException();
		}
		catch (Exception e) {
			//Any other case throw internal server error
			throw new InternalServerErrorException();
		}
	}

	@Transactional
	public User read(Long id) {
		User user = mapper.read(id);
		if (user != null) {
			return user;
		} else {
			throw new NotFoundException();
		}
	}
	
	@Transactional
	public List<User> list() {
		LinkedList<User> userReturnList  = new LinkedList<User>();
		ArrayList<Long> userIds = new ArrayList<Long>(mapper.list());
		
		//Wasn't iterating through every index, only the first one
		for(int i = 0; i < userIds.size(); i++) {
			userReturnList.add(mapper.read(userIds.get(i)));
		}
		
		return userReturnList;
	}
	
	@Transactional
	public User update(Long id, User user) {
		this.read(id);

		try {
			mapper.update(id, user);
		} catch (Exception ex) {
			throw new InternalServerErrorException();
		}
		return user;
	}

	@Transactional
	public User delete(Long id) {
		
		// Remove try catch since it's already handled in the read operation
		User user = this.read(id);

		int count = 0;
		try {
			count = mapper.delete(id);
			userDepartmentMapper.deleteUser(id);
		}
		// Do something with caught exception - throwing internal server error for now
		catch(Exception e){
			throw new InternalServerErrorException();
		}
		if(count < 1)
		{
			throw new NotFoundException();	
		}
		return user;
	}	
}
