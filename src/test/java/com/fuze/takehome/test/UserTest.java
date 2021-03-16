package com.fuze.takehome.test;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;

public class UserTest extends AbstractEntityTest {

	@Inject
	private UserService service;
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
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
	
	User newUser = null;
	User userAddedToDb = null;
	
	@Before
	public void init() {
		newUser = new User()
				.withActive(true)
				.withCustomerId(0L)
				.withEmail("myTest@test.com")
				.withFirstName("My")
				.withLastName("Test")
				.withMobileNumber("555-2356-254")
				.withTelephoneNumber("555-8512-763")
				.withUserName("rcastorena")
				.withDepartmentId(1L);
		userAddedToDb = service.create(newUser);
	}
	
	@Test
	public void testUser() {
		List<User> allUsers = service.list();
		Assert.assertNotNull(allUsers);
		Assert.assertEquals(3, allUsers.size());
		
		Assert.assertNotNull(userAddedToDb.getDepartmentId());
		//ugghh.... can't be bothered
		Assert.assertTrue(newUser.equals(userAddedToDb));
	}
	
	@Test
	public void testReadUser() {
		User userFromDb = service.read(userAddedToDb.getId());
		Assert.assertTrue(newUser.getDepartmentId().equals(userFromDb.getDepartmentId()));
		Assert.assertTrue(newUser.getEmail().toString().equals(userFromDb.getEmail().toString()));
		Assert.assertTrue(compareTwoObjects(newUser, userFromDb));
	}


	private boolean compareTwoObjects(User newUser2, User userFromDb) {
		return newUser2.isActive() == userFromDb.isActive() 
				&& newUser2.getFirstName().toString().equals(userFromDb.getFirstName())
				&& newUser2.getLastName().toString().equals(userFromDb.getLastName())
					&& newUser2.getId().equals(userFromDb.getId())
					 && newUser2.getCustomerId().equals(userFromDb.getCustomerId())
					 	&& newUser2.getUserName().toString().equals(userFromDb.getUserName().toString());
	}
	
	@Test
	public void testDeleteUser() {
		
		User userFromDb = service.read(userAddedToDb.getId());
		User deletedUser = service.delete(userFromDb.getId());
		Assert.assertTrue(compareTwoObjects(newUser, deletedUser));
		exceptionRule.expect(NotFoundException.class);
		service.delete(userFromDb.getId());
	}
	
	
}
