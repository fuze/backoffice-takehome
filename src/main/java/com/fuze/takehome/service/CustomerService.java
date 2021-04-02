package com.fuze.takehome.service;

import javax.inject.Inject;
import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;

import com.fuze.takehome.exceptions.UserServiceExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.fuze.takehome.model.Customer;
import com.fuze.takehome.mybatis.CustomerMapper;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class CustomerService {

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
        Customer fetchedCustomer = mapper.read(id);
        if (fetchedCustomer == null) {
            throw new NotFoundException();
        }
        try {
            mapper.update(id, customer);
        } catch (Exception e) {
            final Throwable cause = e.getCause();
            e.printStackTrace();
            logger.error(e.getMessage());
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new UserServiceExceptions("DB Exception occurred, due to Integrity constraint violation");
            } else if (cause instanceof SQLException) {
                throw new UserServiceExceptions("DB connection issue");
            } else {
                throw new InternalServerErrorException();
            }
        }
        return customer;
    }

    @Transactional
    public Customer delete(Long id) {
        throw new NotSupportedException();
    }
}
