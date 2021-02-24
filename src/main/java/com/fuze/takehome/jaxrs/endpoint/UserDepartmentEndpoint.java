package com.fuze.takehome.jaxrs.endpoint;

import com.fuze.takehome.model.UserDepartment;
import com.fuze.takehome.service.UserDepartmentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user-departments")
public class UserDepartmentEndpoint {
    @Inject
    UserDepartmentService service;

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public UserDepartment create(@Valid @NotNull UserDepartment entity){
        return service.create(entity);
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public UserDepartment read(@NotNull @PathParam("id") Long id){
        return service.readFromUser(id);
    }
}
