package com.fuze.takehome.test;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.junit.Test;

import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;


public class UserTest extends AbstractEntityTest {

	@Inject
	private UserService userService;
	
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
	
	// Suggestion - the in-memory DB should be cleared and re-created for each test method,
	// so that the test data and state are consistent prior to running each test method. 
	// However, test cases will run slightly longer though.
	
	@Test
	public void testList() {
		// test the LIST method
		List<User> allUsers = userService.list();
		Assert.assertNotNull(allUsers);
	}
	
	@Test
	public void testCreateReadDelete() {
		User newUser = this.createDummyUser();
		int numOfUsers = userService.list().size();

		// test the CREATE method
		User userCreated = userService.create(newUser);
		Assert.assertEquals(numOfUsers + 1, userService.list().size());
		Assert.assertNotNull(userCreated);
		this.assertTwoUsers(newUser, userCreated);
		
		// test the READ method
		User userRead = userService.read(userCreated.getId());
		Assert.assertNotNull(userRead);
		this.assertTwoUsers(newUser, userRead);

		// test the DELETE method
		User userDeleted = userService.delete(userCreated.getId());
		Assert.assertEquals(numOfUsers, userService.list().size());
		Assert.assertNotNull(userDeleted);
		this.assertTwoUsers(newUser, userDeleted);
	}
	
	@Test
	public void testExists() {
		Assert.assertTrue(userService.exists(0L));
		Assert.assertFalse(userService.exists(99999L));
	}
	
	@Test(expected = ClientErrorException.class)
	public void testCreateInvalidUser() {
		userService.create(new User()); //user with required properties missing
	}
	
	@Test(expected = ClientErrorException.class)
	public void testCreateInvalidUserWithInvalidCustomerId() {
		userService.create(new User().withCustomerId(99999L)); 
	}
	
	@Test(expected = ClientErrorException.class)
	public void testCreateInvalidUserWithInvalidDepartmentIds() {
		userService.create(new User().withDepartmentIds(Arrays.asList(999999L))); 
	}
	
	@Test(expected = NotFoundException.class)
	public void testReadUserNotFound() {
		userService.read(99999L); //user does not exist
	}
	
	@Test(expected = NotFoundException.class)
	public void testDeleteUserNotFound() {
		userService.delete(99999L); //user does not exist
	}
	
	private User createDummyUser() {
		return new User()
				.withActive(true)
				.withCustomerId(0L)
				.withDepartmentIds(Arrays.asList(0L, 1L))
				.withEmail("myTest@test.com")
				.withFirstName("My")
				.withLastName("Test")
				.withMobileNumber("555-2356-254")
				.withTelephoneNumber("555-8512-763")
				.withUserName("rcastorena");
	}
	
	// check to see if the properties in user1 and user2 objects are the same
	private void assertTwoUsers(User user1, User user2) {
		Assert.assertEquals(user1.getCustomerId(), user2.getCustomerId());
		Assert.assertEquals(user1.getDepartmentIds(), user2.getDepartmentIds());
		Assert.assertEquals(user1.getEmail(), user2.getEmail());
		Assert.assertEquals(user1.getFirstName(), user2.getFirstName());
		Assert.assertEquals(user1.getLastName(), user2.getLastName());
		Assert.assertEquals(user1.getMobileNumber(), user2.getMobileNumber());
		Assert.assertEquals(user1.getTelephoneNumber(), user2.getTelephoneNumber());
		Assert.assertEquals(user1.getUserName(), user2.getUserName());
	}
}
