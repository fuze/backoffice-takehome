package com.fuze.takehome.service;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;

import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.Customer;
import com.fuze.takehome.mybatis.CustomerMapper;

public class CustomerService {
	
	@Inject
	private CustomerMapper customerMapper;

	@Transactional
	public Customer create(Customer customer) {
		customerMapper.create(customer);
		return customer;		
	}

	@Transactional
	public Customer update(Customer customer, Long customerId) {
		if (!this.exists(customerId)) {
			// Throw 404 (Not Found) HTTP error if the provided id not found in DB 
			String message = "Unable to update customer because id '" + customerId + "' not found";
			throw new ClientErrorException(message, Response.status(Response.Status.NOT_FOUND).entity(message).build());
		}
		// ensure the customer object is set with the appropriate id
		customer.setId(customerId);
		
		// update customer in DB
		customerMapper.update(customer);
		
		// return the updated customer object retrieved from DB
		return customerMapper.read(customerId);		
	}

	@Transactional
	public Customer read(Long id) {
		Customer customer = customerMapper.read(id);
		if (customer == null) {
			throw new NotFoundException();
		}
		return customer;
	}
	
	/**
	 * a helper method to see if the customer exists
	 * 
	 * @param id - the customer id
	 * @return true if the customer exists, false otherwise.
	 */
	@Transactional
	public boolean exists(Long id) {
		return customerMapper.read(id) != null;
	}

	@Transactional
	public Customer delete(Long id) {
		throw new NotSupportedException();
	}
}
