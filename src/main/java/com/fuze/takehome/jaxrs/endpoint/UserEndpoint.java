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

import com.fuze.takehome.model.User;
import com.fuze.takehome.service.UserService;

// Suggestion - Including JavaDoc, especially for the class, public fields, and public methods, will be
// helpful for other developers. 

@Path("/users")
// @Produces and @Consumes annotations can be placed at the class level since 
// all the methods in this class use the same MIME type 
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserEndpoint {
	
	@Inject
	// It is better to be more descriptive when naming a field (ie. service -> userService)
	// for better code readability. This is especially important when the code base gets larger and more complex overtime.
	private UserService userService; // change to private

	@POST
	public User create(@Valid @NotNull User entity) {
			return userService.create(entity);
	}
	
	@GET
	@Path("/{id}")
	public User read(@NotNull @PathParam("id") Long id) {
		return userService.read(id);
	}
	
	@GET
	// removed @Path("/") because it is not needed
	public List<User> list() {
		return userService.list();
	}	
	
	@DELETE
	@Path("/{id}")
	// By including the @DELETE annotation, the method below will be triggered by the DELETE HTTP request.
	// Therefore, there is no need to include the word 'delete' in the @Path, which is redundant and considered bad RESTful practices.  
	// The @Path value has been simplified to "/{id}"   
	public User delete(@NotNull @PathParam("id") Long id) {
		return userService.delete(id);
	}
}
