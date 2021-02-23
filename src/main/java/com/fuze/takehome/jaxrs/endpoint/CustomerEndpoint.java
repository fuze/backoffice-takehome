package com.fuze.takehome.jaxrs.endpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.fuze.takehome.model.Customer;
import com.fuze.takehome.service.CustomerService;

@Path("/customers")
public class CustomerEndpoint {
	
	@Inject
	private CustomerService service; 

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Customer create(@Valid @NotNull Customer entity) {
			return service.create(entity);
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Customer read(@NotNull @PathParam("id") Long id) {
		return service.read(id);
	}

	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Customer update(@NotNull @PathParam("id") Long id, @Valid @NotNull Customer entity){
		return service.update(id,entity);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Customer delete(@NotNull @PathParam("id") Long id) {
		return service.delete(id);
	}
}
