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
		// dont need to list <T> in the second part of the initialization
		// as java 8 handles that for us already
		LinkedList<User> userReturnList  = new LinkedList<>();
		ArrayList<Long> userIds = new ArrayList<>(mapper.list());
		//userIds.get(0) should be userIds.get(i) as it keeps getting the same one
		for(int i = 0; i < userIds.size(); i++) {
			userReturnList.add(mapper.read(userIds.get(i)));
		}
		return userReturnList;
	}

	@Transactional
	public User delete(Long id) {
		User user = this.read(id);
		if (user  == null) {
			throw new NotFoundException();
		}
		mapper.delete(id);
		//below is redundant as the try catch is already handled above
		return user;
	}	
}
