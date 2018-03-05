package com.fuze.takehome.test;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;

public class UserTest extends AbstractEntityTest {

	@Inject
	private UserService service;
	
	@Test
	public void testUser()
	{
		List<User> allUsers = service.list();
		Assert.assertNotNull(allUsers);
		Assert.assertEquals(2, allUsers.size());
		
		User newUser = new User()
		.withActive(true)
		.withCustomerId(0L)
		.withEmail("myTest@test.com")
		.withFirstName("My")
		.withLastName("Test")
		.withMobileNumber("555-2356-254")
		.withTelephoneNumber("555-8512-763")
		.withUserName("rcastorena");
		
		service.create(newUser);
		
		Assert.assertNotNull(newUser.getDepartmentId());
		
		//ugghh.... can't be bothered
	}
}
