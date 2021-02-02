package com.fuze.takehome.test;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
		
		// added fax number and department id
		User newUser = new User()
		.withActive(true)
		.withCustomerId(0L)
		.withEmail("myTest@test.com")
		.withFirstName("My")
		.withLastName("Test")
		.withMobileNumber("555-2356-254")
		.withTelephoneNumber("555-8512-763")
		.withFaxNumber("555-1153-145")
		.withUserName("rcastorena");
//		.withDepartmentId(1L);
		
		service.create(newUser);
		
		// checking the size of all users. because a new user added it must be 3
		Assert.assertEquals(3, service.list().size());
		
		// testing all properties that cannot be null or length should be less than 20
		// for service.create() method
		// option1: using javax.Validation with annotation from User class
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);
        Assert.assertTrue(violations.isEmpty());
		
        // option2: using normal assert manually
		Assert.assertNotNull(newUser.getCustomerId());
		Assert.assertNotNull(newUser.getUserName());
		Assert.assertFalse(newUser.getTelephoneNumber().length()>20);
		Assert.assertFalse(newUser.getMobileNumber().length()>20);
		Assert.assertFalse(newUser.getFaxNumber().length()>20);
//		Assert.assertNotNull(newUser.getDepartmentId());
		Assert.assertNotNull(newUser.isActive());
		
		// testing service.read method. since newUser was created above,
		// the method must return the user
		Assert.assertNotNull(service.read(newUser.getId()));
		
		// testing service.delete method. since newUser is being deleted, 
		// the method must return the user
		Assert.assertNotNull(service.delete(newUser.getId()));
		// checking the size of all users. because a new user added it must be back to 2
		Assert.assertEquals(2, service.list().size());
		
		//ugghh.... can't be bothered
	}
}
