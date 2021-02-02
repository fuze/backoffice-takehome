package com.fuze.takehome.service;

import javax.inject.Inject;
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
	public Customer delete(Long id) {
		throw new NotSupportedException();
	}
	
	@Transactional
	public Customer update(Customer newCustomer, Long old_id) {
		int count = 0;
		try {
			count = mapper.update(newCustomer, old_id);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		if (count < 1) {
			throw new NotFoundException();
		}

		return mapper.read(old_id);
	}
}
