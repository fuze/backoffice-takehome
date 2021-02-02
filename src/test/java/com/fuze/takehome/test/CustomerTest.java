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

import com.fuze.takehome.model.Customer;
import com.fuze.takehome.model.Customer.Contact;
import com.fuze.takehome.service.CustomerService;

public class CustomerTest extends AbstractEntityTest {

	@Inject
	private CustomerService service;
	
	/**
	 * Test the Customer Entity and Customer Service.
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
	public void testCustomer() {
		Customer newCustomer = new Customer()
		.withId(1L)
		.withName("Fuze")
		.withActive(true)
		.withContact(new Contact()
				.withEmail("customer@email.com")
				.withFirstName("Terry")
				.withLastName("Jackson"));
		
		service.create(newCustomer);
		
		// testing properties for service.create() method
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(newCustomer);
        Assert.assertTrue(violations.isEmpty());
		
		// testing service.read method. since newCustomer was created above,
		// the method must return the user
		Assert.assertNotNull(service.read(newCustomer.getId()));
		
		// testing service.update method. since newCustomer was created above,
		// the method must return the user
		Customer secondCustomer = new Customer()
			.withName("Custom")
			.withActive(true)
			.withContact(new Contact()
					.withEmail("custom111@email.com")
					.withFirstName("Kelly")
					.withLastName("Me"));
		Customer updatedCustomer = service.update(secondCustomer, newCustomer.getId());
		Assert.assertNotNull(updatedCustomer);
		Assert.assertTrue(service.read(updatedCustomer.getId()).getName() == "Custom");
		Assert.assertTrue(service.read(updatedCustomer.getId()).isActive());
		Assert.assertTrue(service.read(updatedCustomer.getId()).getContact().getEmail() == "custom111@email.com");
		Assert.assertTrue(service.read(updatedCustomer.getId()).getContact().getFirstName() == "Kelly");
		Assert.assertTrue(service.read(updatedCustomer.getId()).getContact().getLastName() == "Me");
		
	}
}
