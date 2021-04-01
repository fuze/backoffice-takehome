/*
 * Testcompleted by: Jay(Jatinder) Singh
 * Question 2-User Test
 */
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
	public void testUser() {
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
		
		
		//ugghh.... can't be bothered
		
		/*
		 * Disclaimer: I haven't created done JUnit testing like ever except for once.
		 * I am still going to give it a try.
		 */
		
		User returnedUser = service.create(newUser);
		
		//Check to if the user has an ID
		Assert.assertNotNull(returnedUser.getId());
		
		// Check to see if the user Count has increased 
		allUsers = service.list();
		Assert.assertEquals(3, allUsers.size());
		
		//Delete the new User and verify it's deleted
		service.delete(returnedUser.getId());
		
		Assert.assertNull(service.read(returnedUser.getId()));
		
		allUsers = service.list();
		Assert.assertEquals(2, allUsers.size());
		
	}
}
