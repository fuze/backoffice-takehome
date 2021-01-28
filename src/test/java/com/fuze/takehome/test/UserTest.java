package com.fuze.takehome.test;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;

public class UserTest extends AbstractEntityTest {

	@Inject
	private UserService service;

	/**
	 * Test the User Entity and User Service.
	 * 
	 * Assert that entity validation is working correctly and the service methods
	 * are doing what they are supposed to be doing.
	 * 
	 * Before this test executes, AbstractEntityTest takes care of initializing
	 * Spring and starting an in-memory DB instance. DummyDataGenerator is invoked
	 * to insert a couple dummy entities into the database.
	 * 
	 */
	@Test
	public void verifyUserCreateReadDelete() {
		final int numUsersBefore = service.list().size();
		// Create
		User expectedUser = new User().withActive(true).withCustomerId(0L)
				.withEmail("verifyUserCreateReadDelete@test.com").withFirstName("test").withLastName("test")
				.withMobileNumber("123-2356-254").withTelephoneNumber("555-8512-763").withUserName("testUserName");
		service.create(expectedUser);
		Assert.assertNotNull(expectedUser.getId());
		Assert.assertEquals(numUsersBefore + 1, service.list().size());

		// Read
		User createdUser = service.read(expectedUser.getId());

		// Well that's awkward, the User class doesn't implement equals or toString
		// So "ugghh.... can't be bothered" to implement those functions
		Assert.assertEquals(expectedUser.isActive(), createdUser.isActive());
		Assert.assertEquals(expectedUser.getCustomerId(), createdUser.getCustomerId());
		Assert.assertEquals(expectedUser.getEmail(), createdUser.getEmail());
		Assert.assertEquals(expectedUser.getFirstName(), createdUser.getFirstName());
		Assert.assertEquals(expectedUser.getLastName(), createdUser.getLastName());
		Assert.assertEquals(expectedUser.getMobileNumber(), createdUser.getMobileNumber());
		Assert.assertEquals(expectedUser.getTelephoneNumber(), createdUser.getTelephoneNumber());
		Assert.assertEquals(expectedUser.getUserName(), createdUser.getUserName());

		// Update (if there were an update function...Seems odd that there isn't

		// Delete
		User deletedUser = service.delete(expectedUser.getId());
		Assert.assertEquals(expectedUser.isActive(), deletedUser.isActive());
		Assert.assertEquals(expectedUser.getCustomerId(), deletedUser.getCustomerId());
		Assert.assertEquals(expectedUser.getEmail(), deletedUser.getEmail());
		Assert.assertEquals(expectedUser.getFirstName(), deletedUser.getFirstName());
		Assert.assertEquals(expectedUser.getLastName(), deletedUser.getLastName());
		Assert.assertEquals(expectedUser.getMobileNumber(), deletedUser.getMobileNumber());
		Assert.assertEquals(expectedUser.getTelephoneNumber(), deletedUser.getTelephoneNumber());
		Assert.assertEquals(expectedUser.getUserName(), deletedUser.getUserName());
		Assert.assertEquals(numUsersBefore, service.list().size());

		// Read after delete
		Exception readAfterDelete = null;
		try {
			service.read(expectedUser.getId());
		} catch (Exception e) {
			readAfterDelete = e;
		}
		Assert.assertEquals(NotFoundException.class, readAfterDelete.getClass());

		// Delete after delete
		Exception deleteAfterDelete = null;
		try {
			service.read(expectedUser.getId());
		} catch (Exception e) {
			deleteAfterDelete = e;
		}
		Assert.assertEquals(NotFoundException.class, deleteAfterDelete.getClass());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void verifyUserWithMissingFieldCustomerIdErrors() {
		User newUser = new User().withActive(true).withUserName("foobar");
		service.create(newUser);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void verifyUserWithMissingFieldUserNameErrors() {
		User newUser = new User().withActive(true).withCustomerId(0L);
		service.create(newUser);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void verifyUserWithLongPhoneNumberErrors() {
		User newUser = new User().withActive(true).withCustomerId(0L)
				.withTelephoneNumber("123456789123456789123456789");
		service.create(newUser);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void verifyUserWithLongMobileNumberErrors() {
		User newUser = new User().withActive(true).withCustomerId(0L).withMobileNumber("123456789123456789123456789");
		service.create(newUser);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void verifyUserWithLongFaxNumberErrors() {
		User newUser = new User().withActive(true).withCustomerId(0L).withFaxNumber("123456789123456789123456789");
		service.create(newUser);
	}
}
