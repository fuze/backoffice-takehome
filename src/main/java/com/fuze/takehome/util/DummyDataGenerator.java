package com.fuze.takehome.util;

import javax.inject.Inject;

import com.fuze.takehome.model.Customer;
import com.fuze.takehome.model.Customer.Contact;
import com.fuze.takehome.model.Department;
import com.fuze.takehome.model.DepartmentUser;
import com.fuze.takehome.model.User;
import com.fuze.takehome.service.CustomerService;
import com.fuze.takehome.service.DepartmentService;
import com.fuze.takehome.service.DepartmentUserService;
import com.fuze.takehome.service.UserService;

import java.util.Arrays;

public class DummyDataGenerator {
    @Inject
    private DepartmentService departmentService;

    @Inject
    private CustomerService customerService;

    @Inject
    private UserService userService;

    @Inject
    private DepartmentUserService departmentUserService;

    public void generateData() {
        generateCustomers();
        generateDepartments();
        generateUsers();
    }

    private void generateCustomers() {
        customerService.create(new Customer()
                .withActive(true)
                .withContact(new Contact()
                        .withEmail("t.guarnieri@toyota.com")
                        .withFirstName("Tom")
                        .withLastName("Guarnieri"))
                .withName("Toyota")
        );
    }

    private void generateDepartments() {
        departmentService.create(new Department()
                .withActive(true)
                .withCustomerId(0L)
                .withDescription("Engineering Group 1")
                .withName("Eng"));

        departmentService.create(new Department()
                .withActive(true)
                .withCustomerId(0L)
                .withDescription("Engineering Group 2")
                .withName("Eng"));
    }

    private void generateUsers() {
        userService.create(new User()
                .withActive(true)
                .withCustomerId(0L)
                .withEmail("r.castorena@toyota.ca")
                .withFirstName("Randy")
                .withLastName("Castorena")
                .withMobileNumber("555-2356-254")
                .withTelephoneNumber("555-8512-763")
                .withUserName("rcastorena"));

        userService.create(new User()
                .withActive(true)
                .withCustomerId(0L)
                .withEmail("w.hertlein@toyota.ca")
                .withFirstName("Wes")
                .withLastName("Hertlein")
                .withMobileNumber("555-7124-554")
                .withTelephoneNumber("555-9965-123")
                .withUserName("whertlein"));

        departmentUserService.create(new DepartmentUser()
                .withDepartmentId(0L)
                .withUserId(0L));
    }
}
