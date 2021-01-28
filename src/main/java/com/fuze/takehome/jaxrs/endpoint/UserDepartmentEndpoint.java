package com.fuze.takehome.jaxrs.endpoint;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fuze.takehome.model.UserDepartment;
import com.fuze.takehome.service.UserDepartmentService;

@Path("/userdepartment")
public class UserDepartmentEndpoint {

	@Inject
	UserDepartmentService service;

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public UserDepartment create(@Valid @NotNull UserDepartment entity) {
		return service.create(entity);
	}

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<UserDepartment> list() {
		return service.list();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserDepartment read(@NotNull @PathParam("id") final Long id) {
		return service.read(id);
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserDepartment delete(@NotNull @PathParam("id") Long id) {
		return service.delete(id);
	}
}
