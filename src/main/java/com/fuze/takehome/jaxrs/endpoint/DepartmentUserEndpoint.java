package com.fuze.takehome.jaxrs.endpoint;

import com.fuze.takehome.exceptions.UserServiceExceptions;
import com.fuze.takehome.model.DepartmentUser;
import com.fuze.takehome.service.DepartmentUserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/departmentuser")
public class DepartmentUserEndpoint {
    @Inject
    private DepartmentUserService departmentUserService;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@Valid @NotNull DepartmentUser entity) {
        DepartmentUser relation = null;
        try {
            relation = departmentUserService.create(entity);
        } catch (UserServiceExceptions e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(201).entity(relation).build();
    }
    /* We can add endpoint for readUserDepartments(Long userId) && readDepartmentUsers(Long deptId)*/
}
