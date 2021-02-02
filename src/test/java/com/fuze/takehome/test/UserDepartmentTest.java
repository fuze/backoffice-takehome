package com.fuze.takehome.test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.fuze.takehome.model.User;
import com.fuze.takehome.model.Department;
import com.fuze.takehome.model.UserDepartment;
import com.fuze.takehome.service.UserService;
import com.fuze.takehome.service.DepartmentService;
import com.fuze.takehome.service.UserDepartmentService;

public class UserDepartmentTest extends AbstractEntityTest {

	@Inject
	private UserService userService;
	
	@Inject
	private DepartmentService departmentService;
	
	@Inject
	private UserDepartmentService userDepartmentService;
	
	/**
	 * Test the UserDepartment Entity and UserDepartment Service.
	 * 
	 * Assert that entity validation is working correctly and
	 * the service methods are doing what they are supposed to be doing.
	 * 
	 */
	@Test
	public void testUserDepartment() {
		User user = userService.create(new User()
				.withActive(true)
				.withCustomerId(0L)
				.withEmail("myTest@test.com")
				.withFirstName("My")
				.withLastName("Test")
				.withMobileNumber("555-2356-254")
				.withTelephoneNumber("555-8512-763")
				.withFaxNumber("555-1153-145")
				.withUserName("rcastorena"));
		Department department = departmentService.create(new Department()
				.withActive(true)
				.withCustomerId(0L)
				.withDescription("new Department")
				.withName("Hardware"));

		
		UserDepartment newUserDepartment = new UserDepartment()
			.withUserId(user.getId())
			.withDepartmentId(department.getId());
		
		userDepartmentService.create(newUserDepartment);
		
		// testing all properties
		// for service.create() method
		// option1: using javax.Validation with annotation from User class
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDepartment>> violations = validator.validate(newUserDepartment);
        Assert.assertTrue(violations.isEmpty());
		
		// testing service.read method. since newUser was created above,
		// the method must return the user
		Assert.assertNotNull(userDepartmentService.readFromUser(user.getId()));
		Assert.assertTrue(userDepartmentService.readFromUser(user.getId()).getDepartmentId() == department.getId());

		Assert.assertNotNull(userDepartmentService.readFromDepartment(department.getId()));
		Assert.assertTrue(userDepartmentService.readFromDepartment(department.getId()).getUserId() == user.getId());
	}
}
