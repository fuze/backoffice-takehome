package com.fuze.takehome.service;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;

import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.Customer;
import com.fuze.takehome.model.User;
import com.fuze.takehome.mybatis.CustomerMapper;

public class CustomerService {
	
	@Inject
	private CustomerMapper mapper;

	@Transactional
	public Customer create(Customer customer) {
		mapper.create(customer);
		return customer;		
	}

	@Transactional
	public Customer read(Long id) {
		Customer customer = mapper.read(id);
		if (customer != null) {
			return customer;
		} else {
			throw new NotFoundException();
		}
	}
	
	@Transactional
	public Customer update(Long id, Customer customer) {
		this.read(id);

		try {
			mapper.update(id, customer);
		} catch (Exception ex) {
			throw new InternalServerErrorException();
		}
		return customer;
	}

	@Transactional
	public Customer delete(Long id) {
		throw new NotSupportedException();
	}
}
