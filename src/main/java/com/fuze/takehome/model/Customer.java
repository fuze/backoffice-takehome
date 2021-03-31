package com.fuze.takehome.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class Customer {

    private Long id;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "active cannot be null")
    private boolean active;

    @Valid
    private Contact contact = new Contact();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Customer withId(Long id) {
        this.id = id;
        return this;
    }

    public Customer withName(String name) {
        this.name = name;
        return this;
    }

    public Customer withActive(boolean active) {
        this.active = active;
        return this;
    }

    public Customer withContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public static class Contact {
        @Email
        private String email;
        private String firstName;
        private String lastName;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Contact withEmail(String email) {
            this.email = email;
            return this;
        }

        public Contact withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Contact withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

    }

}
