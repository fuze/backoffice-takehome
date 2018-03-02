package com.fuze.takehome.dao;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryUserDAO implements UserDAO {

	private static List<User> users = Arrays.asList(
			new User(1L, "Matt", "Murdock", 1L),
			new User(2L, "Jessica", "Jones", 2L),
			new User(3L, "Luke", "Cage", 2L),
			new User(4L, "Danny", "Rand", 3L)
			// ... and many more
			);
	
	@Override
	public User getUser(Long id) {
		return users.stream().filter(u -> u.getId() == id).findFirst().get();
	}

	@Override
	public List<User> getUsers() {
		return users;
	}

	@Override
	public List<User> getUsers(Predicate<User> predicate) {
		return users.stream().filter(predicate).collect(Collectors.toList());
	}
}
