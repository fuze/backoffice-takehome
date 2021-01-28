
/*
 * Some general comments about the repo as a whole:
 * 1. There are numerous whitespace inconsistencies.  You should run a linter/the auto formatter against this file.
 * If the team has a custom linter use that, otherwise a good ol' right click > source > format will do the trick.
 * 
 * 2.  I noticed that the models do not have toString() or equals() methods implemented.  While not a problem, it would be a nice for testing.
 * quality of life improvement for devs.
 * 
 * 3.  Error responses include stack traces.  This is not a big deal for a backoffice API, assuming we trust the people calling the API.
 * 
 * 4.  Nothing is "final".  It's a design choice, but standard practice is to use finals wherever possible.
 */

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

@Path("/users")
public class UserEndpoint {

	@Inject
	UserService service;

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public User create(@Valid @NotNull User entity) {
		return service.create(entity);
	}

	/*
	 * It depends how this API is supposed to be implemented, but I would expect a
	 * request with id "foobar" to return an http 400 (bad request) 
	 * 
	 * I would need to see the API documentation; which I assume was written and
	 * agreed to before any developer touched this code, right?
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public User read(@NotNull @PathParam("id") Long id) {
		return service.read(id);
	}

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> list() {
		return service.list();
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public User delete(@NotNull @PathParam("id") Long id) {
		return service.delete(id);
	}
}
