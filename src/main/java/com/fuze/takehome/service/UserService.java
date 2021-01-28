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
		User user = mapper.read(id); // Consider adding some error handling

		/*
		 * The 'else' in this if statement is redundant.
		 * 
		 * Nitpick: I prefer fail-fast where the response is checked for validity and
		 * errors are thrown first. If the checks are passed, then the response is
		 * returned after.
		 */
		if (user == null) { // I updated this function
			throw new NotFoundException();
		}
		return user;
	}

	@Transactional
	public List<User> list() {
		/*
		 * 
		 * 1. This doesn't iterate because the for loop's index is not used in the get
		 * request.
		 * 
		 * 2. Casting the Collection returned by mapper.list() to an ArrayList doesn't
		 * have an apparent purpose. It has a fixed size once generated and will be
		 * accessed sequentially so the benefits of an ArrayList are not used.
		 * 
		 * 3. I would prefer that this iteration be replaced by a map function, but
		 * that's a style choice.
		 */
		LinkedList<User> userReturnList = new LinkedList<User>();
		ArrayList<Long> userIds = new ArrayList<Long>(mapper.list());
		for (int i = 0; i < userIds.size(); i++) {
			userReturnList.add(mapper.read(userIds.get(i))); // I Updated this so I could test it
		}
		return userReturnList;
	}

	@Transactional
	public User delete(Long id) {
		User user = this.read(id);
		if (user == null) { // Nice use of a fail-fast approach :)
			throw new NotFoundException();
		}
		int count = 0;
		try {
			count = mapper.delete(id);
		} catch (Exception e) {
			// This will fail silently. Consider adding some useful logs here
			// I also assume the API spec calls for an http 500 in this case
		}

		/*
		 * It should't be possible for the mapper to return a value greater than one
		 * because we are accessing the table from the identity column, but weirder things
		 * have happened.
		 * 
		 */
		if (count < 1) {
			throw new NotFoundException();
		}
		return user;
	}
}
