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
	public UserMapper mapper;

	@Transactional
	public User create(User user) {
		mapper.create(user);
		return user;		
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
		for(int i = 0; i < userIds.size(); i++) {
			userReturnList.add(mapper.read(userIds.get(0)));
		}
		return userReturnList;
	}

	@Transactional
	public User delete(Long id) {
		User user = this.read(id);
		if (user  == null) {
			throw new NotFoundException();
		}
		int count = 0;
		try {
			count = mapper.delete(id);
		}
		catch(Exception e){
		}
		if(count < 1)
		{
			throw new NotFoundException();	
		}
		return user;
	}	
}
