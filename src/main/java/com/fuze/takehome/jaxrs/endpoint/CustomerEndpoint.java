package com.fuze.takehome.jaxrs.endpoint;

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

import com.fuze.takehome.model.Customer;
import com.fuze.takehome.service.CustomerService;

@Path("/customers")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class CustomerEndpoint {
	
	@Inject
	private CustomerService customerService; 

	@POST
	public Customer create(@Valid @NotNull Customer entity) {
			return customerService.create(entity);
	}

	// Implemented custom annotated @PATCH method, see com.fuze.takehome.jaxrs.endpoint.PATCH
	@PATCH
	@Path("/{id}")
	public Customer update(@Valid @NotNull Customer entity, @PathParam("id") Long id) {
		// Note, I assume we are supporting the PATCH method (https://tools.ietf.org/html/rfc5789) and not the PUT method; 
		// the PATCH method supports both full and partial modification of an existing HTTP resource.
		return customerService.update(entity, id);
	}

	@GET
	@Path("/{id}")
	public Customer read(@NotNull @PathParam("id") Long id) {
		return customerService.read(id);
	}
	
	@DELETE
	@Path("/{id}")
	public Customer delete(@NotNull @PathParam("id") Long id) {
		return customerService.delete(id);
	}
	
}
