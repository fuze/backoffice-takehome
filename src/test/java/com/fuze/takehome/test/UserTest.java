package com.fuze.takehome.test;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;

public class UserTest extends AbstractEntityTest {

	@Inject
	private UserService service;

	/**
	 * Test the User Entity and User Service.
	 * 
	 * Assert that entity validation is working correctly and the service methods are doing what they are supposed to be doing.
	 * 
	 * Before this test executes, AbstractEntityTest takes care of initializing Spring and starting an in-memory DB instance. DummyDataGenerator is invoked to insert a couple dummy entities into the
	 * database.
	 * 
	 */

	@Test
	public void testUser() {
		List<User> allUsers = service.list();
		Assert.assertNotNull(allUsers);
		Assert.assertEquals(2, allUsers.size());

		// Test create invalid customer with duplicate department IDs
		User dupe = new User()
				.withActive(true)
				.withCustomerId(0L)
				// Ensure constraint respected
				.withDepartmentIds(Arrays.asList(0L, 0L))
				.withEmail("myTest@test.com")
				.withFirstName("My")
				.withLastName("Test")
				.withMobileNumber("555-2356-254")
				.withTelephoneNumber("555-8512-763")
				.withUserName("rcastorena");

		exceptionRule.expect(DuplicateKeyException.class);
		service.create(dupe);

		allUsers = service.list();
		Assert.assertEquals(2, allUsers.size());

		// Test create valid user
		User newUser = new User()
				.withActive(true)
				.withCustomerId(0L)
				.withDepartmentIds(Arrays.asList(0L, 1L))
				.withEmail("myTest@test.com")
				.withFirstName("My")
				.withLastName("Test")
				.withMobileNumber("555-2356-254")
				.withTelephoneNumber("555-8512-763")
				.withUserName("rcastorena");

		service.create(newUser);
		Assert.assertNotNull(newUser.getId());

		allUsers = service.list();
		Assert.assertEquals(3, allUsers.size());

		// Test read user from dummy data
		User testReadUser = service.read(0L);
		Assert.assertNotNull(testReadUser.getCustomerId());

		// Test delete user from dummy data
		service.delete(testReadUser.getId());

		exceptionRule.expect(NotFoundException.class);
		service.read(testReadUser.getId());

		allUsers = service.list();
		Assert.assertEquals(2, allUsers.size());
	}
}
