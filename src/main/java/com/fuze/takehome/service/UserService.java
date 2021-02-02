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
		// don't need to create a new arraylist. we can just use collection stream function
//		ArrayList<Long> userIds = new ArrayList<Long>(mapper.list());
//		for(int i = 0; i < userIds.size(); i++) {
//			userReturnList.add(mapper.read(userIds.get(0)));
//		}
		mapper.list().stream().forEach(id -> {
			userReturnList.add(mapper.read(id));
		});
		
		return userReturnList;
	}

	@Transactional
	public User delete(Long id) {
//		User user = this.read(id);
//		if (user  == null) {
//			throw new NotFoundException();
//		}
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
		// above, we're checking user's existence three times (line52[in this.read], line53, line62)
		User user = mapper.read(id);
		// instead, it'll check user's existence once in next line
		if (user != null) {
			try {
				mapper.delete(id);
			}
			catch(Exception e){
				throw new RuntimeException(String.format("Failed to delete User %d.", id), e);
			}
		} else {
			throw new NotFoundException();
		}
		
		return user;
	}	
}
