package com.fuze.takehome.jaxrs.endpoint;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fuze.takehome.exceptions.UserServiceExceptions;
import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;

@Path("/users")
public class UserEndpoint {
    /* Updated REST Endpoints to handle error gracefully and provide standard error codes.
     * Each endpoint must handle exception thrown from UserService class.
     * One option could be to create class UserServiceExceptions.java to handle all kinds of exceptions that can occur in UserService.java
     * Security of API use SSL/TLS security
     */

    @Inject
    private UserService service;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@Valid @NotNull User entity) {
        User u = null;
        try {
            u = service.create(entity);
        } catch (UserServiceExceptions e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(201).entity(u).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response read(@NotNull @PathParam("id") Long id) {
        try {
            return Response.status(200).entity(service.read(id)).build();
        } catch (NotFoundException e) {
            return Response.status(404).entity("The user with the id " + id + " does not exist.").build();
        } catch (UserServiceExceptions e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    //Allow pagination for listing all users.(suggested change)
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> list() {
        return service.list();
    }

    @DELETE
    @Path("/{id}") //'DELETE' type http request,no need to add verb delete in path.
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@NotNull @PathParam("id") Long id) {
        try {
            return Response.status(200).entity(service.delete(id)).build();
        } catch (NotFoundException e) {
            return Response.status(404).entity("The user with the id " + id + " does not exist.").build();
        } catch (UserServiceExceptions e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
