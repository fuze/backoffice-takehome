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
	
	/**
	 * Test the User Entity and User Service.
	 * 
	 * Assert that entity validation is working correctly and
	 * the service methods are doing what they are supposed to be doing.
	 * 
	 * Before this test executes, AbstractEntityTest takes care of initializing
	 * Spring and starting an in-memory DB instance. DummyDataGenerator is
	 * invoked to insert a couple dummy entities into the database. 
	 * 
	 */
	
	@Test
	public void testListUser() {
		List<User> allUsers = service.list();
		Assert.assertNotNull(allUsers);
	}
	
	@Test
	public void testCreateUser() {
		
		User newUser = new User()
		.withActive(true)
		.withCustomerId(0L)
		.withEmail("myTest@test.com")
		.withFirstName("My")
		.withLastName("Test")
		.withMobileNumber("555-2356-254")
		.withTelephoneNumber("555-8512-763")
		.withUserName("rcastorena")
		.withDepartmentId(0L);
		
		User createdUser = service.create(newUser);
		Assert.assertEquals(newUser, createdUser);
	}
	
	@Test
	public void testReadUser() {
		// Create user to be read
		User newUser = new User()
		.withActive(true)
		.withCustomerId(0L)
		.withEmail("myTest@test.com")
		.withFirstName("My")
		.withLastName("Test")
		.withMobileNumber("555-2356-254")
		.withTelephoneNumber("555-8512-763")
		.withUserName("rcastorena")
		.withDepartmentId(0L);
		
		User createdUser = service.create(newUser);
		Assert.assertEquals(newUser, createdUser);
		
		User returnedUser = service.read(createdUser.getId());
		Assert.assertTrue(areUsersEqual(newUser, returnedUser));
	}
	
	@Test
	public void testDeleteUser() {
		
		// Create user to be deleted
		User newUser = new User()
		.withActive(true)
		.withCustomerId(0L)
		.withEmail("myTest@test.com")
		.withFirstName("My")
		.withLastName("Test")
		.withMobileNumber("555-2356-254")
		.withTelephoneNumber("555-8512-763")
		.withUserName("rcastorena")
		.withDepartmentId(0L);
				
		User createdUser = service.create(newUser);
		Assert.assertEquals(newUser, createdUser);
		
		User deletedUser = service.delete(createdUser.getId());
		Assert.assertTrue(areUsersEqual(newUser, deletedUser));
	}
	
	private boolean areUsersEqual(User user1, User user2) {
		if (user1.isActive() != (user2.isActive())) {
			return false;
		}
		if (!user1.getCustomerId().equals(user2.getCustomerId())) {
			return false;
		}
		if (!user1.getEmail().equals(user2.getEmail())) {
			return false;
		}
		if (!user1.getFirstName().equals(user2.getFirstName())) {
			return false;
		}
		if (!user1.getLastName().equals(user2.getLastName())) {
			return false;
		}
		if (!user1.getMobileNumber().equals(user2.getMobileNumber())) {
			return false;
		}
		if (!user1.getTelephoneNumber().equals(user2.getTelephoneNumber())) {
			return false;
		}
		if (!user1.getUserName().equals(user2.getUserName())) {
			return false;
		}
		if (!user1.getDepartmentIds().equals(user2.getDepartmentIds())) {
			return false;
		}
		return true;
	}
}
