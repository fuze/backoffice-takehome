package com.fuze.takehome.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.transaction.annotation.Transactional;

import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.UserDepartmentMapper;
import com.fuze.takehome.mybatis.UserMapper;

public class UserService {

	@Inject
	private UserMapper mapper; // Make private

	@Inject
	private UserDepartmentMapper udMapper;

	@Transactional
	public User create(User user) {
		mapper.create(user);
		user.getDepartmentIds().forEach(departmentId -> udMapper.create(user.getId(), departmentId));
		return user;
	}

	@Transactional
	public User read(Long id) {
		User user = mapper.read(id);
		if (user == null) {
			throw new NotFoundException();
		}
		return user;
	}

	@Transactional
	public List<User> list() {
		LinkedList<User> userReturnList = new LinkedList<User>();
		ArrayList<Long> userIds = new ArrayList<Long>(mapper.list());

		userIds.forEach(id -> userReturnList.add(mapper.read(id)));

		return userReturnList;
	}

	@Transactional
	public User delete(Long id) {
		User user = mapper.read(id);
		if (user == null) {
			throw new NotFoundException();
		}

//		int count = 0;
//		try {
//			count = mapper.delete(id);
//		}
//		catch(Exception e){
//		}
//		if(count < 1)
//		{
//			throw new NotFoundException();	
//		}

		int confirm = mapper.delete(id);
		if (confirm == 0) {
			throw new InternalServerErrorException("Could not delete");
		}

		return user;
	}
}
